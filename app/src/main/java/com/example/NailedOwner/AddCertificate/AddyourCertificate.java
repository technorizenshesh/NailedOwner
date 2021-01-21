package com.example.NailedOwner.AddCertificate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.NailedOwner.FileUtil;
import com.example.NailedOwner.HomeScree.HomeActivity;
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
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddyourCertificate extends AppCompatActivity {

    private Button btn_next_certificate;
    private boolean isCertificate =false;
    private ImageView img_camera;
    private RelativeLayout RR_certificate;
    File imageFilePath_certificate;
    private ProgressBar progressBar;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addyour_certificate);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }
        preference = new Preference(this);
        progressBar=findViewById(R.id.progressBar);
        btn_next_certificate=findViewById(R.id.btn_next_certificate);
        RR_certificate=findViewById(R.id.RR_certificate);
        img_camera=findViewById(R.id.img_camera);

        btn_next_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isCertificate)
                {
                    if (preference.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);

                        UploadCertificate();

                    } else {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                R.string.checkInternet, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }


                }else
                {
                    Toast.makeText(AddyourCertificate.this, "Please Enter Certificate.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RR_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(AddyourCertificate.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddyourCertificate.this);
                                    startActivityForResult(intent, 1);
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
    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddyourCertificate.this);
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddyourCertificate.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_certificate = FileUtil.from(AppController.getContext(), resultUri);
                    img_camera.setImageBitmap(bitmap);

                    isCertificate =true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File compressedImage = new Compressor(AppController.getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_certificate);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AppController.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void UploadCertificate() {

        MultipartBody.Part imgFile = null;
        if (imageFilePath_certificate == null) {
        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), imageFilePath_certificate);
            imgFile = MultipartBody.Part.createFormData("image", imageFilePath_certificate.getName(), requestFileOne);
        }

        String UserId = Preference.get(AddyourCertificate.this,Preference.KEY_USER_ID);
        String category_id = Preference.get(AddyourCertificate.this,Preference.KEY_CategoryId);


        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), UserId);

        RequestBody Category_id = RequestBody.create(MediaType.parse("text/plain"), category_id);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .USER_addcertificate(userID,Category_id, imgFile);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(AddyourCertificate.this, message, Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(AddyourCertificate.this, HomeActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(AddyourCertificate.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddyourCertificate.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
