package com.example.NailedOwner.AllServices.UpdateServices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.NailedOwner.AddServicesTwo.GetServicesDataModel;
import com.example.NailedOwner.AddServicesTwo.GetServicesModel;
import com.example.NailedOwner.AllSalonPhoto.AllSalonPhoto;
import com.example.NailedOwner.FileUtil;
import com.example.NailedOwner.HomeScree.HomeActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.R;
import com.example.NailedOwner.SelectCategory.CategoryAdapter;
import com.example.NailedOwner.SelectCategory.ChoseCategoryModel;
import com.example.NailedOwner.SelectCategory.getHomeModel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.NailedOwner.Volley.AppController.getContext;

public class UpdateServices extends AppCompatActivity implements IApiResponse {

    private Activity activity;
    private Button btn_next_services;
    private Spinner Spn_Category;
    ArrayList<ChoseCategoryModel> Category = new ArrayList<>();

    private Preference preference;
    private ProgressBar progressBar;
    private String android_id = "";
    private String categoryId = "";

    private EditText edt_serviceName;
    private EditText edt_servicePrice;
    private EditText edt_serviceduration;
    private EditText edt_service_Customization;
    private EditText edt_service_Description;

    private RelativeLayout RR_img_one;
    private RelativeLayout RR_img_two;
    private RelativeLayout RR_img_three;
    private RelativeLayout RR_img_four;
    private RelativeLayout RR_img_five;
    private RelativeLayout RR_img_six;
    private RelativeLayout RR_img_seven;

    private TextView txt_logo_one;
    private TextView txt_logo_two;
    private TextView txt_logo_three;
    private TextView txt_logo_four;
    private TextView txt_logo_five;
    private TextView txt_logo_sex;
    private TextView txt_logo_seven;

    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;
    private ImageView img_four;
    private ImageView img_five;
    private ImageView img_sex;
    private ImageView img_seven;
    private boolean isProfileIage = false;

    Bitmap bitmap;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Bitmap bitmap4;
    Bitmap bitmap5;
    Bitmap bitmap6;

    private File imageFilePath_one = null;
    private File imageFilePath_two = null;
    private File imageFilePath_three = null;
    private File imageFilePath_four = null;
    private File imageFilePath_five = null;
    private File imageFilePath_sex = null;
    private File imageFilePath_seven = null;

    boolean isimage = false;

    String ServicesName = "";
    String servicePrice = "";
    String servicesDuration = "";
    String Customization = "";
    String Description = "";

    TextView txt_count_img;

    String Type = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_services);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }
        activity = UpdateServices.this;
        findview();
        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(this);

       /* Intent intent=getIntent();
        if(intent!=null)
        {
            Type = intent.getStringExtra("Type").toString();
        }*/

        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getCategory();

            getServicesDewtails();

        } else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


        btn_next_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServicesName = edt_serviceName.getText().toString();
                servicePrice = edt_servicePrice.getText().toString();
                servicesDuration = edt_serviceduration.getText().toString();
                Customization = edt_service_Customization.getText().toString();
                Description = edt_service_Description.getText().toString();

                if (ServicesName.equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please Enter Service Name.", Toast.LENGTH_SHORT).show();

                } else if (servicePrice.equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please Enter service Price.", Toast.LENGTH_SHORT).show();

                } else if (servicesDuration.equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please Enter service Duration..", Toast.LENGTH_SHORT).show();

                } else if (Description.equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please Enter service Description.", Toast.LENGTH_SHORT).show();
                } else {

                    if (preference.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);

                        UpdateServicesApi();

                    } else {

                        Toast.makeText(UpdateServices.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        RR_img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                    if (report.areAllPermissionsGranted()) {
                                        Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                        startActivityForResult(intent, 1);
                                    } else {
                                        showSettingDialogue();
                                    }

                                    //  showPictureDialog(1,0);

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

        RR_img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 2);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    //  showPictureDialog(3,2);

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

        RR_img_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 3);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    //showPictureDialog(5,4);

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

        RR_img_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 4);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    //  showPictureDialog(7,6);

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

        RR_img_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 5);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    // showPictureDialog(9,8);

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

        RR_img_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 6);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    // showPictureDialog(11,10);

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

        RR_img_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateServices.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(UpdateServices.this);
                                    startActivityForResult(intent, 7);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    //showPictureDialog(13,12);

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


    private void findview() {

        txt_count_img = findViewById(R.id.txt_count_img);
        img_one = findViewById(R.id.img_one);
        img_two = findViewById(R.id.img_two);
        img_three = findViewById(R.id.img_three);
        img_four = findViewById(R.id.img_four);
        img_five = findViewById(R.id.img_five);
        img_sex = findViewById(R.id.img_sex);
        img_seven = findViewById(R.id.img_seven);

        txt_logo_one = findViewById(R.id.txt_logo_one);
        txt_logo_two = findViewById(R.id.txt_logo_two);
        txt_logo_three = findViewById(R.id.txt_logo_three);
        txt_logo_four = findViewById(R.id.txt_logo_four);
        txt_logo_five = findViewById(R.id.txt_logo_five);
        txt_logo_sex = findViewById(R.id.txt_logo_sex);
        txt_logo_seven = findViewById(R.id.txt_logo_seven);

        RR_img_one = findViewById(R.id.RR_img_one);
        RR_img_two = findViewById(R.id.RR_img_two);
        RR_img_three = findViewById(R.id.RR_img_three);
        RR_img_four = findViewById(R.id.RR_img_four);
        RR_img_five = findViewById(R.id.RR_img_five);
        RR_img_six = findViewById(R.id.RR_img_six);
        RR_img_seven = findViewById(R.id.RR_img_seven);

        edt_serviceName = findViewById(R.id.edt_serviceName);
        edt_servicePrice = findViewById(R.id.edt_servicePrice);
        edt_serviceduration = findViewById(R.id.edt_serviceduration);
        edt_service_Customization = findViewById(R.id.edt_service_Customization);
        edt_service_Description = findViewById(R.id.edt_service_Description);
        progressBar = findViewById(R.id.progressBar);
        Spn_Category = findViewById(R.id.Spn_Category);
        btn_next_services = findViewById(R.id.btn_next_services);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_one = FileUtil.from(getContext(), resultUri);
                    img_one.setImageBitmap(bitmap);
                    img_one.setVisibility(View.VISIBLE);
                    txt_logo_one.setVisibility(View.GONE);
                    txt_count_img.setText("(1/7)");
                    isimage = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_one);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_two = FileUtil.from(getContext(), resultUri);
                    img_two.setImageBitmap(bitmap);
                    img_two.setVisibility(View.VISIBLE);
                    txt_logo_two.setVisibility(View.GONE);

                    txt_count_img.setText("(2/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_two);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_three = FileUtil.from(getContext(), resultUri);
                    img_three.setImageBitmap(bitmap);
                    img_three.setVisibility(View.VISIBLE);
                    txt_logo_three.setVisibility(View.GONE);

                    txt_count_img.setText("(3/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_three);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_four = FileUtil.from(getContext(), resultUri);
                    img_four.setImageBitmap(bitmap);
                    img_four.setVisibility(View.VISIBLE);
                    txt_logo_four.setVisibility(View.GONE);

                    txt_count_img.setText("(4/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_four);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_five = FileUtil.from(getContext(), resultUri);
                    img_five.setImageBitmap(bitmap);
                    img_five.setVisibility(View.VISIBLE);
                    txt_logo_five.setVisibility(View.GONE);

                    txt_count_img.setText("(5/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_five);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_sex = FileUtil.from(getContext(), resultUri);
                    img_sex.setImageBitmap(bitmap);
                    img_sex.setVisibility(View.VISIBLE);
                    txt_logo_sex.setVisibility(View.GONE);

                    txt_count_img.setText("(6/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_sex);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 7) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateServices.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_seven = FileUtil.from(getContext(), resultUri);
                    img_seven.setImageBitmap(bitmap);
                    img_seven.setVisibility(View.VISIBLE);
                    txt_logo_seven.setVisibility(View.GONE);

                    txt_count_img.setText("(7/7)");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_seven);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateServices.this);
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

    private void Category(final ArrayList<ChoseCategoryModel> Category, Spinner SpinnerNew) {

        CategoryAdapter customAdapter1 = new CategoryAdapter(UpdateServices.this, Category);
        SpinnerNew.setAdapter(customAdapter1);

        SpinnerNew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = Category.get(position).getId().toString();
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    public void getCategory() {

        HashMap<String, String> map = new HashMap<>();

        ApiRequest apiRequest = new ApiRequest(UpdateServices.this, this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_get_category, Constants.USER_get_category, map, Request.Method.GET);

    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_get_category.equalsIgnoreCase(tag_json_obj)) {

            progressBar.setVisibility(View.GONE);

            if (response != null) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();

                    if (status.equalsIgnoreCase("1")) {

                        getHomeModel finalArray = new Gson().fromJson(response, new TypeToken<getHomeModel>() {
                        }.getType());

                        Category = (ArrayList<ChoseCategoryModel>) finalArray.getResult();

                        Category(Category, Spn_Category);

                    } else {
                        Toast.makeText(this, "Your have entered wrong email & password", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
    }

    private void UpdateServicesApi() {

        MultipartBody.Part imgFile_one = null;
        MultipartBody.Part imgFile_two = null;
        MultipartBody.Part imgFile_three = null;
        MultipartBody.Part imgFile_four = null;
        MultipartBody.Part imgFile_five = null;
        MultipartBody.Part imgFile_sex = null;
        MultipartBody.Part imgFile_seven = null;

        if (imageFilePath_one == null) {


        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), imageFilePath_one);
            RequestBody requestFileTwo = RequestBody.create(MediaType.parse("image/*"), imageFilePath_two);
            RequestBody requestFilethree = RequestBody.create(MediaType.parse("image/*"), imageFilePath_three);
            RequestBody requestFilefour = RequestBody.create(MediaType.parse("image/*"), imageFilePath_four);
            RequestBody requestFilefive = RequestBody.create(MediaType.parse("image/*"), imageFilePath_five);
            RequestBody requestFilesex = RequestBody.create(MediaType.parse("image/*"), imageFilePath_sex);
            RequestBody requestFileseven = RequestBody.create(MediaType.parse("image/*"), imageFilePath_seven);

            imgFile_one = MultipartBody.Part.createFormData("image", imageFilePath_one.getName(), requestFileOne);
            imgFile_two = MultipartBody.Part.createFormData("image1", imageFilePath_two.getName(), requestFileTwo);
            imgFile_three = MultipartBody.Part.createFormData("image2", imageFilePath_three.getName(), requestFilethree);
            imgFile_four = MultipartBody.Part.createFormData("image3", imageFilePath_four.getName(), requestFilefour);
            imgFile_five = MultipartBody.Part.createFormData("image4", imageFilePath_five.getName(), requestFilefive);
            imgFile_sex = MultipartBody.Part.createFormData("image5", imageFilePath_sex.getName(), requestFilesex);
            imgFile_seven = MultipartBody.Part.createFormData("image6", imageFilePath_seven.getName(), requestFileseven);
        }

        String services_id = Preference.get(UpdateServices.this, Preference.KEY_Services_id);

        RequestBody Services_id = RequestBody.create(MediaType.parse("text/plain"), services_id);
        RequestBody servicesName = RequestBody.create(MediaType.parse("text/plain"), ServicesName);
        RequestBody ServicePrice = RequestBody.create(MediaType.parse("text/plain"), servicePrice);
        RequestBody service_duration = RequestBody.create(MediaType.parse("text/plain"), servicesDuration);
        RequestBody CategoryId = RequestBody.create(MediaType.parse("text/plain"), categoryId);
        RequestBody customization = RequestBody.create(MediaType.parse("text/plain"), Customization);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), Description);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "Yes");

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .update_service(Services_id, servicesName, ServicePrice, service_duration, CategoryId, customization, description,imgFile_one, imgFile_two, imgFile_three, imgFile_four, imgFile_five, imgFile_sex, imgFile_seven);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                        //  Intent intent=new Intent(UpdateServices.this, AddServicesTwo.class);
                        Intent intent = new Intent(UpdateServices.this, HomeActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //  uploadImageBtn.setEnabled(true);
                //  cameraIcon.setEnabled(true);
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getServicesDewtails(){

        String services_id = Preference.get(UpdateServices.this, Preference.KEY_Services_id);

        Call<ServicesModelUpdate> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_services_details(services_id);

        call.enqueue(new Callback<ServicesModelUpdate>() {
            @Override
            public void onResponse(Call<ServicesModelUpdate> call, Response<ServicesModelUpdate> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    ServicesModelUpdate finallylist = response.body();

                    String status   = finallylist.getStatus ();

                    String message   = finallylist.getStatus ();

                    if (status.equalsIgnoreCase("1")) {


                        edt_serviceName.setText(finallylist.getResult().getName());
                        edt_service_Customization.setText(finallylist.getResult().getCustomization());
                        edt_servicePrice.setText(finallylist.getResult().getPrice());
                        edt_service_Description.setText(finallylist.getResult().getDescription());
                        edt_serviceduration.setText(finallylist.getResult().getServiceDuration());

                        String ImageUrl_One= finallylist.getResult().getImage().toString();
                        String ImageUrl_two= finallylist.getResult().getImage1().toString();
                        String ImageUrl_three= finallylist.getResult().getImage2().toString();
                        String ImageUrl_four= finallylist.getResult().getImage3().toString();
                        String ImageUrl_five= finallylist.getResult().getImage4().toString();
                        String ImageUrl_sex= finallylist.getResult().getImage5().toString();
                        String ImageUrl_seven= finallylist.getResult().getImage6().toString();

                        imageFilePath_one = new File(ImageUrl_One);
                        imageFilePath_two = new File(ImageUrl_two);
                        imageFilePath_three = new File(ImageUrl_three);
                        imageFilePath_four = new File(ImageUrl_four);
                        imageFilePath_five = new File(ImageUrl_five);
                        imageFilePath_sex = new File(ImageUrl_sex);
                        imageFilePath_seven = new File(ImageUrl_seven);



                        if(ImageUrl_One!=null)
                        {
                            img_one.setVisibility(View.VISIBLE);
                            txt_logo_one.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_One).placeholder(R.drawable.galllary_img)
                                    .into(img_one);

                        }
                        if(ImageUrl_two!=null)
                        {
                            img_two.setVisibility(View.VISIBLE);
                            txt_logo_two.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_two).placeholder(R.drawable.galllary_img)
                                    .into(img_two);
                        }

                        if(ImageUrl_three!=null)
                        {
                            img_three.setVisibility(View.VISIBLE);
                            txt_logo_three.setVisibility(View.GONE);
                            Glide.with(UpdateServices.this).load(ImageUrl_three).placeholder(R.drawable.galllary_img)
                                    .into(img_three);
                        }

                        if(ImageUrl_four!=null)
                        {
                            img_four.setVisibility(View.VISIBLE);
                            txt_logo_four.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_four).placeholder(R.drawable.galllary_img)
                                    .into(img_four);
                        }

                        if(ImageUrl_five!=null)
                        {
                            img_five.setVisibility(View.VISIBLE);
                            txt_logo_five.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_five).placeholder(R.drawable.galllary_img)
                                    .into(img_five);
                        }
                        if(ImageUrl_sex!=null)
                        {
                            img_sex.setVisibility(View.VISIBLE);
                            txt_logo_sex.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_sex).placeholder(R.drawable.galllary_img)
                                    .into(img_sex);

                        } if(ImageUrl_seven!=null)
                        {
                            img_seven.setVisibility(View.VISIBLE);
                            txt_logo_seven.setVisibility(View.GONE);

                            Glide.with(UpdateServices.this).load(ImageUrl_seven).placeholder(R.drawable.galllary_img)
                                    .into(img_seven);
                        }


                    } else {

                        Toast.makeText(UpdateServices.this, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ServicesModelUpdate> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //  uploadImageBtn.setEnabled(true);
                //  cameraIcon.setEnabled(true);
                Toast.makeText(UpdateServices.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
