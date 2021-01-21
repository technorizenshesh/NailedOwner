package com.example.NailedOwner.SignUpScreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.AllSalonPhoto.GetImageSaloon;
import com.example.NailedOwner.FileUtil;
import com.example.NailedOwner.GPSTracker;
import com.example.NailedOwner.LoginScreen.LoginActivity;
import com.example.NailedOwner.Preference;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity{

    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_phone;
    private EditText edt_address;
    private EditText edt_selected_area;
    private EditText edt_zipcode;
    private EditText edt_password;
    private EditText edt_Confirm_password;
    private EditText edt_Gender;
    private Button btn_signUp;
    private TextView txt_login;
    private TextView txt_dob;
    private RelativeLayout RR_Dob;

    private ProgressBar progressBar;
    private Preference preference;
    private String android_id ="";
    String DOb ="";
    RelativeLayout RR_one_doc;
    RelativeLayout RR_two_doc;
    RelativeLayout RR_profile;

    ImageView img_doc_two;
    ImageView img_doc_one;

    File profile_pic =null;
    File DOc_one =null;
    File DOc_two=null;

    CircleImageView user_profile;
    ImageView img;
    ImageView upload;

    String UserName="";
    String email="";
    String mobile="";
    String gender="";
    String address="";
    String selected_area="";
    String ZipCode="";
    String password="";
    String confrmPassword="";

    boolean isProfile=false;

    Spinner spinnergender;
    private String code[] ={"Gender","Male","Female"};
    private int flags[]= {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};

    GPSTracker gpsTracker;
    String latitude ="";
    String longitude ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }
        spinnergender =findViewById(R.id.spinnergender);

        GenderSpinnerAdapter customAdapter=new GenderSpinnerAdapter(SignUpActivity.this,flags,code);
        spinnergender.setAdapter(customAdapter);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        preference = new Preference(this);

        findview();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RR_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int  mMonth = c.get(Calendar.MONTH);
                int  mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //  RR_booking_Date.setVisibility( View.VISIBLE );
                                //  txt_time.setVisibility(View.VISIBLE);
                                view.setVisibility(View.VISIBLE);

                                DOb = (dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                               String DOb1 = (year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                                String NewDate = getDate(DOb1);

                                txt_dob.setText(NewDate);

                            }
                        }, mYear, mMonth, mDay);
              //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        RR_one_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(SignUpActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(SignUpActivity.this);
                                    startActivityForResult(intent, 1);

                                } else {
                                    showSettingDialogue();
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        RR_two_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(SignUpActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(SignUpActivity.this);
                                    startActivityForResult(intent, 2);

                                } else {
                                    showSettingDialogue();
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        RR_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(SignUpActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(SignUpActivity.this);
                                    startActivityForResult(intent, 3);

                                } else {
                                    showSettingDialogue();
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });
    }

    private void validation() {

         UserName=edt_name.getText().toString();
         email=edt_email.getText().toString();
         mobile=edt_phone.getText().toString();
         address=edt_address.getText().toString();
         selected_area=edt_selected_area.getText().toString();
         ZipCode=edt_zipcode.getText().toString();
         password=edt_password.getText().toString();
         confrmPassword=edt_Confirm_password.getText().toString();

        if(!isProfile)
        {
            Toast.makeText(this, "Please upload profile picture.", Toast.LENGTH_SHORT).show();

        }else if(UserName.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter FullName.", Toast.LENGTH_SHORT).show();

        }else if(!isValidEmail(email))
        {
            Toast.makeText(this, "Please Enter email.", Toast.LENGTH_SHORT).show();

        }else if(mobile.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter mobile.", Toast.LENGTH_SHORT).show();

        }else if(gender.equalsIgnoreCase("Gender"))
        {
            Toast.makeText(this, "Please Enter Gender.", Toast.LENGTH_SHORT).show();

        }else if(DOb.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Date Of birth...", Toast.LENGTH_SHORT).show();

        }else if(address.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter address.", Toast.LENGTH_SHORT).show();

        }else if(selected_area.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter City.", Toast.LENGTH_SHORT).show();

        }else if(ZipCode.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Pin Code.", Toast.LENGTH_SHORT).show();


        }else if(password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter password.", Toast.LENGTH_SHORT).show();


        }else if(confrmPassword.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Confirm Password.", Toast.LENGTH_SHORT).show();

        }else if(!confrmPassword.equalsIgnoreCase(password))
        {
            Toast.makeText(this, "Don't match Password.", Toast.LENGTH_SHORT).show();

        }else
        {
            if (preference.isNetworkAvailable()) {

               // btn_signUp.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);

                signMethod();

            }else {
                Toast.makeText(this,  R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void findview() {
        progressBar=findViewById(R.id.progressBar);
        txt_login=findViewById(R.id.txt_login);
        btn_signUp= findViewById(R.id.btn_signUp);
        edt_name= findViewById(R.id.edt_name);
        edt_email= findViewById(R.id.edt_email);
        edt_phone= findViewById(R.id.edt_phone);
        edt_address= findViewById(R.id.edt_address);
        edt_selected_area= findViewById(R.id.edt_selected_area);
        edt_zipcode= findViewById(R.id.edt_zipcode);
        edt_password= findViewById(R.id.edt_password);
        edt_Gender= findViewById(R.id.edt_Gender);
        edt_Confirm_password= findViewById(R.id.edt_Confirm_password);
        RR_Dob= findViewById(R.id.RR_Dob);
        txt_dob= findViewById(R.id.txt_dob);
        RR_two_doc= findViewById(R.id.RR_two_doc);
        RR_one_doc= findViewById(R.id.RR_one_doc);
        img_doc_two= findViewById(R.id.img_doc_two);
        img_doc_one= findViewById(R.id.img_doc_one);
        RR_profile= findViewById(R.id.RR_profile);
        user_profile= findViewById(R.id.user_profile);
        img= findViewById(R.id.img);
        upload= findViewById(R.id.upload);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                gender = code[position];

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker=new GPSTracker(this);

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

   /* public void signUpMethod(String user_name,String email,String mobile,String address,String city,String zip_code,String password){

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name",user_name);
        map.put("email",email);
        map.put("mobile",mobile);
        map.put("address",address);
        map.put("city",city);
        map.put("zip_code",zip_code);
        map.put("password",password);
        map.put("lat","25.00");
        map.put("lon","25.00");
        map.put("register_id",android_id);


        ApiRequest apiRequest = new ApiRequest(SignUpActivity.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_SIGNUP, Constants.USER_SIGNUP,map, Request.Method.POST);
    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_SIGNUP.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);
            btn_signUp.setEnabled(true);

            if (response !=null) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();
                    String result = jsonObject.getString("result").toString();

                    if(status.equalsIgnoreCase("1"))
                    {
                        SignUpModel finalArray = new Gson().fromJson(response,new TypeToken<SignUpModel>(){}.getType());

                        Toast.makeText(this, finalArray.getMessage(), Toast.LENGTH_SHORT).show();

                        preference.save(SignUpActivity.this, Preference.KEY_USER_ID,finalArray.getResult().getId());

                        Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else
                    {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        btn_signUp.setEnabled(true);
        Toast.makeText(this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
    }
*/

    private String getDate(String Date)
    {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateObj = null;
        try {
            dateObj = curFormater.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM yyyy");
        String newDateStr = postFormater.format(dateObj);
        return newDateStr;
    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSetting();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    DOc_one = FileUtil.from(SignUpActivity.this, resultUri);
                    //   training_documents.setImageBitmap(bitmap);
                  //  training_documents.setImageResource(R.drawable.check_box);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    img_doc_one.setImageResource(R.drawable.check_box);

                  //  isTraining = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                 File compressedImage_one = new Compressor(SignUpActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(DOc_one);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_one);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                  DOc_two = FileUtil.from(SignUpActivity.this, resultUri);
                    // dbs.setImageBitmap(bitmap);
                    img_doc_two.setImageResource(R.drawable.check_box);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                  //  isDbs = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                  File  compressedImage_two = new Compressor(SignUpActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(DOc_two);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    profile_pic = FileUtil.from(SignUpActivity.this, resultUri);
                    user_profile.setImageBitmap(bitmap);
                    upload.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                   // img_doc_two.setImageResource(R.drawable.check_box);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                     isProfile = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File  compressedImage_two = new Compressor(SignUpActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(profile_pic);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void signMethod()
    {
        MultipartBody.Part imgFile_one = null;
        MultipartBody.Part imgFile_two = null;
        MultipartBody.Part imgFile_three = null;

        if (profile_pic == null) {

        }else if(DOc_one ==null)
        {

        }else if(DOc_two ==null)
        {

        }else
        {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), profile_pic);
            RequestBody requestFileTwo = RequestBody.create(MediaType.parse("image/*"), DOc_one);
            RequestBody requestFilethree = RequestBody.create(MediaType.parse("image/*"), DOc_two);

            imgFile_one = MultipartBody.Part.createFormData("image",profile_pic.getName(), requestFileOne);
            imgFile_two = MultipartBody.Part.createFormData("document1",DOc_one.getName(), requestFileTwo);
            imgFile_three = MultipartBody.Part.createFormData("document2", DOc_two.getName(), requestFilethree);

        }

        RequestBody Username =RequestBody.create(MediaType.parse("text/plain"),UserName);
        RequestBody Email =RequestBody.create(MediaType.parse("text/plain"),email);
        RequestBody Mobile =RequestBody.create(MediaType.parse("text/plain"),mobile);
        RequestBody Gender =RequestBody.create(MediaType.parse("text/plain"),gender);
        RequestBody Address =RequestBody.create(MediaType.parse("text/plain"),address);
        RequestBody city =RequestBody.create(MediaType.parse("text/plain"),selected_area);
        RequestBody Zip_code =RequestBody.create(MediaType.parse("text/plain"),ZipCode);
        RequestBody Password =RequestBody.create(MediaType.parse("text/plain"),password);
        RequestBody Lat =RequestBody.create(MediaType.parse("text/plain"),latitude);
        RequestBody Lon =RequestBody.create(MediaType.parse("text/plain"),longitude);
        RequestBody Register_id =RequestBody.create(MediaType.parse("text/plain"),"jgg");
        RequestBody Dob_id =RequestBody.create(MediaType.parse("text/plain"),DOb);

        retrofit2.Call<SignUpModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .USER_signup1(Username,Address,Email,Password,Mobile,Lat,Lon,city,Zip_code
                ,Dob_id,Gender,Register_id,imgFile_one,imgFile_two,imgFile_three);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(retrofit2.Call<SignUpModel> call, Response<SignUpModel> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    SignUpModel finallylist = response.body();

                     String status = finallylist.getStatus();
                     String message = finallylist.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    } else {

                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }




}
