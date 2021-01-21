package com.example.NailedOwner.AddServicesTwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.NailedOwner.AddServices.AddServicesActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.Utills.RetrofitClients;
import com.example.NailedOwner.AddCertificate.AddyourCertificate;
import  com.example.NailedOwner.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServicesTwo extends AppCompatActivity {

    private Button btn_next_services;
    private ImageView add_services;
    private Preference preference;
    private ProgressBar progressBar;
    String android_id ="";

    private RecyclerView recycler_getServces;
    private GetServicesRecyclerViewAdapter mAdapter;
    private ArrayList<GetServicesDataModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_two);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

        progressBar=findViewById(R.id.progressBar);
        recycler_getServces=findViewById(R.id.recycler_getServces);
        btn_next_services=findViewById(R.id.btn_next_services);
        add_services=findViewById(R.id.add_services);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(this);

        btn_next_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AddServicesTwo.this, AddyourCertificate.class);
                startActivity(intent);
            }
        });
        add_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddServicesTwo.this, AddServicesActivity.class);
                intent.putExtra("Type","NoServices");
                startActivity(intent);
            }
        });


        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getServicesListMethod();

        }else {

            Toast.makeText(AddServicesTwo.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }

    }

    private void setAdapter(ArrayList<GetServicesDataModel> modelList) {

        mAdapter = new GetServicesRecyclerViewAdapter(AddServicesTwo.this, this.modelList);
        recycler_getServces.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddServicesTwo.this);
        recycler_getServces.setLayoutManager(linearLayoutManager);
        recycler_getServces.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GetServicesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetServicesDataModel model) {

            }
        });
    }

    private void getServicesListMethod(){

        String UserId = Preference.get(AddServicesTwo.this,Preference.KEY_USER_ID);

        Call<GetServicesModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .USER_get_service(UserId);

        call.enqueue(new Callback<GetServicesModel>() {
            @Override
            public void onResponse(Call<GetServicesModel> call, Response<GetServicesModel> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    GetServicesModel finallylist = response.body();

                    String status   = finallylist.getStatus ();
                    String message   = finallylist.getStatus ();
                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<GetServicesDataModel>) finallylist.getResult();

                        Toast.makeText(AddServicesTwo.this, message, Toast.LENGTH_SHORT).show();

                        setAdapter(modelList);

                    } else {

                        Toast.makeText(AddServicesTwo.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<GetServicesModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //  uploadImageBtn.setEnabled(true);
                //  cameraIcon.setEnabled(true);
                Toast.makeText(AddServicesTwo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
