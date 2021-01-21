package com.example.NailedOwner.AddSaloonPhoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.NailedOwner.AddTiming.AddTiming;
import com.example.NailedOwner.FileUtil;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.Utills.RetrofitClients;
import com.example.NailedOwner.Volley.AppController;
import  com.example.NailedOwner.R;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddsaloonPhotos extends AppCompatActivity {

   private Button btn_next_add_saloon;
    public ViewPager mViewPager;
    private static final Integer[] xMen =
            {R.drawable.logo, R.drawable.logo, R.drawable.logo};
    private ArrayList<Integer> xMenArray = new ArrayList<>();
    private int currentPage = 0;

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


    private ProgressBar progressBar;
    private Preference preference;

    Bitmap bitmap;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Bitmap bitmap4;
    Bitmap bitmap5;
    Bitmap bitmap6;

    private File imageFilePath_one= null;
    private File imageFilePath_two= null;
    private File imageFilePath_three= null;
    private File imageFilePath_four= null;
    private  File imageFilePath_five= null;
    private File imageFilePath_sex= null;
    private File imageFilePath_seven= null;

    private File compressedImage_one= null;
    private File compressedImage_two= null;
    private File compressedImage_three= null;
    private File compressedImage_four= null;
    private File compressedImage_five= null;
    private File compressedImage_sex= null;
    private File compressedImage_seven= null;

    boolean imageOne=false;
    boolean imageTro=false;
    boolean imageThree=false;
    boolean imageFour=false;
    boolean imageFive=false;
    boolean imageSix=false;
    boolean imageSeven=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsaloon_photos);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

        preference = new Preference(this);

           findview();


        btn_next_add_saloon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!imageOne)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image One.", Toast.LENGTH_SHORT).show();

                }else if(!imageTro)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Two.", Toast.LENGTH_SHORT).show();

                }else if(!imageThree)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Three.", Toast.LENGTH_SHORT).show();

                }else if(!imageFour)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Four.", Toast.LENGTH_SHORT).show();

                }else if(!imageFive)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Five.", Toast.LENGTH_SHORT).show();

                }else if(!imageSix)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Sex .", Toast.LENGTH_SHORT).show();

                }else if(!imageSeven)
                {
                    Toast.makeText(AddsaloonPhotos.this, "Please Select Image Seven.", Toast.LENGTH_SHORT).show();

                }else {

                    if (preference.isNetworkAvailable()) {

                        btn_next_add_saloon.setEnabled(false);

                        progressBar.setVisibility(View.VISIBLE);

                        SaloonImageUpload();

                    } else {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                R.string.checkInternet, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        RR_img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
                                    startActivityForResult(intent, 1);

                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                   // showPictureDialog(1,0);

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

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
                                    startActivityForResult(intent, 2);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                   // showPictureDialog(3,2);

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

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
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

        RR_img_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
                                    startActivityForResult(intent, 4);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                   // showPictureDialog(7,6);

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

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
                                    startActivityForResult(intent, 5);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    //showPictureDialog(9,8);

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

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
                                    startActivityForResult(intent, 6);
                                    //Toast.makeText(CreateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                  //  showPictureDialog(11,10);

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

                Dexter.withActivity(AddsaloonPhotos.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddsaloonPhotos.this);
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
        //initilizeSlider();

    }

    private void findview() {
        progressBar=findViewById(R.id.progressBar);
        btn_next_add_saloon=findViewById(R.id.btn_next_add_saloon);
        mViewPager = findViewById(R.id.pager_slider);
        img_one=findViewById(R.id.img_one);
        img_two=findViewById(R.id.img_two);
        img_three=findViewById(R.id.img_three);
        img_four=findViewById(R.id.img_four);
        img_five=findViewById(R.id.img_five);
        img_sex=findViewById(R.id.img_sex);
        img_seven=findViewById(R.id.img_seven);

        txt_logo_one=findViewById(R.id.txt_logo_one);
        txt_logo_two=findViewById(R.id.txt_logo_two);
        txt_logo_three=findViewById(R.id.txt_logo_three);
        txt_logo_four=findViewById(R.id.txt_logo_four);
        txt_logo_five=findViewById(R.id.txt_logo_five);
        txt_logo_sex=findViewById(R.id.txt_logo_sex);
        txt_logo_seven=findViewById(R.id.txt_logo_seven);

        RR_img_one=findViewById(R.id.RR_img_one);
        RR_img_two=findViewById(R.id.RR_img_two);
        RR_img_three=findViewById(R.id.RR_img_three);
        RR_img_four=findViewById(R.id.RR_img_four);
        RR_img_five=findViewById(R.id.RR_img_five);
        RR_img_six=findViewById(R.id.RR_img_six);
        RR_img_seven=findViewById(R.id.RR_img_seven);
    }


    private void initilizeSlider() {

        for (int i = 0; i < xMen.length; i++)

            xMenArray.add(xMen[i]);

        mViewPager.setAdapter(new SliderAdapterHome(AddsaloonPhotos.this, xMenArray));


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {

                if (currentPage == xMenArray.size()) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);

            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

    }
    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddsaloonPhotos.this);
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_one = FileUtil.from(AppController.getContext(), resultUri);
                    img_one.setImageBitmap(bitmap);
                    img_one.setVisibility(View.VISIBLE);
                    txt_logo_one.setVisibility(View.GONE);

                    imageOne = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_one = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_one);
                Log.e("ActivityTag", "imageFilePAth: " + compressedImage_one);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_two = FileUtil.from(AppController.getContext(), resultUri);
                    img_two.setImageBitmap(bitmap);
                    img_two.setVisibility(View.VISIBLE);
                    txt_logo_two.setVisibility(View.GONE);

                    imageTro=true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_two = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_two);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_three = FileUtil.from(AppController.getContext(), resultUri);
                    img_three.setImageBitmap(bitmap);
                    img_three.setVisibility(View.VISIBLE);
                    txt_logo_three.setVisibility(View.GONE);

                    imageThree =true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_three = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_three);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_three);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_four = FileUtil.from(AppController.getContext(), resultUri);
                    img_four.setImageBitmap(bitmap);
                    img_four.setVisibility(View.VISIBLE);
                    txt_logo_four.setVisibility(View.GONE);
                    imageFour =true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_four = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_four);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_four);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_five = FileUtil.from(AppController.getContext(), resultUri);
                    img_five.setImageBitmap(bitmap);
                    img_five.setVisibility(View.VISIBLE);
                    txt_logo_five.setVisibility(View.GONE);

                    imageFive =true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_five = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_five);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_five);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else  if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_sex = FileUtil.from(AppController.getContext(), resultUri);
                    img_sex.setImageBitmap(bitmap);
                    img_sex.setVisibility(View.VISIBLE);
                    txt_logo_sex.setVisibility(View.GONE);

                    imageSix =true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     compressedImage_sex = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_sex);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_sex);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else  if (requestCode == 7) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddsaloonPhotos.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_seven = FileUtil.from(AppController.getContext(), resultUri);
                    img_seven.setImageBitmap(bitmap);
                    img_seven.setVisibility(View.VISIBLE);
                    txt_logo_seven.setVisibility(View.GONE);
                    imageSeven =true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage_seven = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_seven);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_seven);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void SaloonImageUpload(){

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

            imgFile_one = MultipartBody.Part.createFormData("image",imageFilePath_one.getName(), requestFileOne);
            imgFile_two = MultipartBody.Part.createFormData("image1",imageFilePath_two.getName(), requestFileTwo);
                imgFile_three = MultipartBody.Part.createFormData("image2", imageFilePath_three.getName(), requestFilethree);
            imgFile_four = MultipartBody.Part.createFormData("image3", imageFilePath_four.getName(), requestFilefour);
            imgFile_five = MultipartBody.Part.createFormData("image4", imageFilePath_five.getName(), requestFilefive);
            imgFile_sex = MultipartBody.Part.createFormData("image5", imageFilePath_sex.getName(), requestFilesex);
            imgFile_seven = MultipartBody.Part.createFormData("image6", imageFilePath_seven.getName(), requestFileseven);

        }

        String UserId = Preference.get(AddsaloonPhotos.this,Preference.KEY_USER_ID);
        String Category_id = Preference.get(AddsaloonPhotos.this,Preference.KEY_CategoryId);

        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), UserId);
        //RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), Category_id);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .USER_SaloonImageUpload(userID,imgFile_one,imgFile_two,imgFile_three,imgFile_four,imgFile_five,imgFile_sex,imgFile_seven);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(AddsaloonPhotos.this, message, Toast.LENGTH_SHORT).show();

                        String Add_saloon_id = resultOne.getString("id");

                        Preference.save(AddsaloonPhotos.this,Preference.KEY_Add_saloon_id,Add_saloon_id);

                        Intent intent=new Intent(AddsaloonPhotos.this, AddTiming.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(AddsaloonPhotos.this, result, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(AddsaloonPhotos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    
  /*  public void SaloonImageUpload(final Bitmap bitmap, final Bitmap bitmap1, final Bitmap bitmap2, final Bitmap bitmap3, final Bitmap bitmap4, final Bitmap bitmap5, final Bitmap bitmap6){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.BASE_URL1+Constants.USER_add_salon,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            btn_next_add_saloon.setEnabled(true);
                            progressBar.setVisibility(View.GONE);

                            JSONObject jsonObject = new JSONObject(String.valueOf(response.data));

                            String status = jsonObject.getString("status").toString();
                            String message = jsonObject.getString("message").toString();

                            if (status.equalsIgnoreCase("1")) {

                                Toast.makeText(AddsaloonPhotos.this, message+"", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(AddsaloonPhotos.this, AddTiming.class);
                                startActivity(intent);


                            } else {

                                Intent intent=new Intent(AddsaloonPhotos.this, AddTiming.class);
                                startActivity(intent);

                                Toast.makeText(AddsaloonPhotos.this, message+"", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {

                            Intent intent=new Intent(AddsaloonPhotos.this, AddTiming.class);
                            startActivity(intent);

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btn_next_add_saloon.setEnabled(true);
                        Intent intent=new Intent(AddsaloonPhotos.this, AddTiming.class);
                        startActivity(intent);
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if(bitmap != null && !bitmap.equals("")) {
                    params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }if(bitmap1 != null && !bitmap1.equals("")) {
                    params.put("image1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap1)));
                }if(bitmap2 != null && !bitmap2.equals("")) {
                    params.put("image2", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));
                }if(bitmap3 != null && !bitmap3.equals("")) {
                    params.put("image3", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap3)));
                }if(bitmap4 != null && !bitmap4.equals("")) {
                    params.put("image4", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap4)));
                }if(bitmap5 != null && !bitmap5.equals("")) {
                    params.put("image5", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap5)));
                }if(bitmap6 != null && !bitmap6.equals("")) {
                    params.put("image6", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap6)));
                }
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();

                String user_id = Preference.get(AddsaloonPhotos.this,Preference.KEY_USER_ID);
                String category_id = Preference.get(AddsaloonPhotos.this,Preference.KEY_CategoryId);

                map.put("user_id",user_id);
                map.put("category_id",category_id);

                return map;
            }

        };

        //adding the request to volley
        volleyMultipartRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }*/
}
