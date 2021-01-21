package com.example.NailedOwner.AllSalonPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.NailedOwner.AddServicesTwo.AddServicesTwo;
import com.example.NailedOwner.AddServicesTwo.GetServicesDataModel;
import com.example.NailedOwner.AddServicesTwo.GetServicesModel;
import com.example.NailedOwner.AddServicesTwo.GetServicesRecyclerViewAdapter;
import com.example.NailedOwner.Preference;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSalonPhoto extends AppCompatActivity {

    private ProgressBar progressBar;
    private RelativeLayout RR_back;
    private RecyclerView recycler_getSalon_image;
    private GetSaloonRecyclerViewAdapter mAdapter;
    private ArrayList<GetImageSaloonData> modelList = new ArrayList<>();

    ImageView img_one;
    ImageView img_two;
    ImageView img_three;
    ImageView img_four;
    ImageView img_five;
    ImageView img_sex;
    ImageView img_seven;
    TextView txt_tool;
    private Preference preference;
    String Type ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_salon_photo);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }
        txt_tool = findViewById(R.id.txt_tool);

        preference = new Preference(AllSalonPhoto.this);
        progressBar=findViewById(R.id.progressBar);
        RR_back=findViewById(R.id.RR_back);
        img_one=findViewById(R.id.img_one);
        img_two=findViewById(R.id.img_two);
        img_three=findViewById(R.id.img_three);
        img_four=findViewById(R.id.img_four);
        img_five=findViewById(R.id.img_five);
        img_sex=findViewById(R.id.img_sex);
        img_seven=findViewById(R.id.img_seven);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getSaloonImage();

        }else {

            Toast.makeText(AllSalonPhoto.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }

    }


   /* private void setAdapter(ArrayList<GetImageSaloonData> modelList) {

        mAdapter = new GetSaloonRecyclerViewAdapter(AllSalonPhoto.this, this.modelList);
        recycler_getSalon_image.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllSalonPhoto.this);
        recycler_getSalon_image.setLayoutManager(linearLayoutManager);
        recycler_getSalon_image.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GetSaloonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetServicesDataModel model) {

            }
        });
    }*/

    private void getSaloonImage(){

        String UserId = Preference.get(AllSalonPhoto.this,Preference.KEY_USER_ID);

        Call<GetImageSaloon> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_salon_image(UserId);

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
                        Toast.makeText(AllSalonPhoto.this, message, Toast.LENGTH_SHORT).show();
                        String ImageUrl_One = finallylist.getResult().get(0).getImage();
                        String ImageUrl_two = finallylist.getResult().get(0).getImage1();
                        String ImageUrl_three = finallylist.getResult().get(0).getImage2();
                        String ImageUrl_four = finallylist.getResult().get(0).getImage3();
                        String ImageUrl_five = finallylist.getResult().get(0).getImage4();
                        String ImageUrl_sex = finallylist.getResult().get(0).getImage5();
                        String ImageUrl_seven = finallylist.getResult().get(0).getImage6();

                        if(ImageUrl_One!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_One).placeholder(R.drawable.galllary_img)
                                    .into(img_one);

                        }
                        if(ImageUrl_two!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_two).placeholder(R.drawable.galllary_img)
                                    .into(img_two);
                        }

                        if(ImageUrl_three!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_three).placeholder(R.drawable.galllary_img)
                                    .into(img_three);
                        }

                        if(ImageUrl_four!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_four).placeholder(R.drawable.galllary_img)
                                    .into(img_four);
                        }

                        if(ImageUrl_five!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_five).placeholder(R.drawable.galllary_img)
                                    .into(img_five);
                        }
                        if(ImageUrl_sex!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_sex).placeholder(R.drawable.galllary_img)
                                    .into(img_sex);

                        } if(ImageUrl_seven!=null)
                        {
                            Glide.with(AllSalonPhoto.this).load(ImageUrl_seven).placeholder(R.drawable.galllary_img)
                                    .into(img_seven);
                        }

                    } else {

                        Toast.makeText(AllSalonPhoto.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AllSalonPhoto.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
