package com.example.NailedOwner.GetCertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.NailedOwner.AllSalonPhoto.AllSalonPhoto;
import com.example.NailedOwner.AllSalonPhoto.GetImageSaloon;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCertificateActivity extends AppCompatActivity {

    private Preference preference;
    private ProgressBar progressBar;
    private RelativeLayout RR_back;
    private ImageView img_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_certificate);

        progressBar=findViewById(R.id.progressBar);

        preference = new Preference(GetCertificateActivity.this);

        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getSaloonImageCertificate();

        }else {

            Toast.makeText(GetCertificateActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }

    }

    private void getSaloonImageCertificate(){

        String UserId = Preference.get(GetCertificateActivity.this,Preference.KEY_USER_ID);

        Call<GetImageSaloon> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_certificate(UserId);

        call.enqueue(new Callback<GetImageSaloon>() {
            @Override
            public void onResponse(Call<GetImageSaloon> call, Response<GetImageSaloon> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    GetImageSaloon finallylist = response.body();

                    String status   = finallylist.getStatus ();
                    String message   = finallylist.getStatus ();

                    if (status.equalsIgnoreCase("1")) {

                        //  modelList= (ArrayList<GetImageSaloonData>) finallylist.getResult();
                        Toast.makeText(GetCertificateActivity.this, message, Toast.LENGTH_SHORT).show();

                        String ImageUrl_One = finallylist.getResult().get(0).getImage();

                        if(ImageUrl_One!=null)
                        {
                            Glide.with(GetCertificateActivity.this).load(ImageUrl_One).placeholder(R.drawable.galllary_img)
                                    .into(img_one);
                        }

                    } else {

                        Toast.makeText(GetCertificateActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<GetImageSaloon> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //  uploadImageBtn.setEnabled(true);
                //  cameraIcon.setEnabled(true);
                Toast.makeText(GetCertificateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}