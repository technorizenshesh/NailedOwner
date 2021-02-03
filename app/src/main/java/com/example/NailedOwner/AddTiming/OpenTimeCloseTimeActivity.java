package com.example.NailedOwner.AddTiming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.R;
import com.example.NailedOwner.SelectCategory.SelectCategory;
import com.example.NailedOwner.Utills.RetrofitClients;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenTimeCloseTimeActivity extends AppCompatActivity implements IApiResponse {

    private RecyclerView recycler_open_time;
    private RecyclerView recycler_close_time;
    private ArrayList<DaysModelData> modelList_closetime = new ArrayList<>();
    private ArrayList<DaysModelData> modelList_Opentime = new ArrayList<>();
    private CloseTimeRecyclerViewAdapter  mAdapter_close;
    private OpenTimeRecyclerViewAdapter  mAdapter_Open;
    private Button btn_next_timing;
    private Preference preference;
    private ProgressBar progressBar;
    String Weekly_closeDay="";


     public static String Monday_open ="";
    public static String tuesDay_open ="";
    public static  String wednesday_open ="";
    public static  String thursday_open ="";
    public static  String Friday_open ="";
    public static  String saturday_open ="";
    public static   String sunday_open ="";

    public static String Monday_close ="";
    public static String tuesDay_close ="";
    public static String wednesday_close ="";
    public static String thursday_close ="";
    public static  String Friday_close ="";
    public static  String saturday_close ="";
    public static  String sunday_close ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_time_close_time);


        recycler_close_time=findViewById(R.id.recycler_close_time);
        recycler_open_time=findViewById(R.id.recycler_open_time);
        progressBar=findViewById(R.id.progressBar);
        btn_next_timing=findViewById(R.id.btn_next_timing);

        Intent intent=getIntent();

        if(intent !=null)
        {
            Weekly_closeDay=intent.getStringExtra("Weekly_closeDay");
        }
        preference = new Preference(this);

        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getOpenDays(Weekly_closeDay);

        }else {
            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        btn_next_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   validation();
                if (preference.isNetworkAvailable()) {

                    progressBar.setVisibility(View.VISIBLE);

                    AddTiming();

                }else {

                    Toast.makeText(OpenTimeCloseTimeActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void setAdapterOpenTime(ArrayList<DaysModelData> modelList_Opentime) {


        mAdapter_Open = new OpenTimeRecyclerViewAdapter(OpenTimeCloseTimeActivity.this,modelList_Opentime);
        recycler_open_time.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OpenTimeCloseTimeActivity.this);
        recycler_open_time.setLayoutManager(new GridLayoutManager(OpenTimeCloseTimeActivity.this, 3));
        recycler_open_time.setAdapter(mAdapter_Open);

        mAdapter_Open.SetOnItemClickListener(new OpenTimeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DaysModelData model) {


            }
        });
    }

    private void setAdapterCloseTiime(ArrayList<DaysModelData> modelList_closetime) {

        mAdapter_close = new CloseTimeRecyclerViewAdapter(OpenTimeCloseTimeActivity.this, modelList_closetime);
        recycler_close_time.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OpenTimeCloseTimeActivity.this);
        recycler_close_time.setLayoutManager(new GridLayoutManager(OpenTimeCloseTimeActivity.this, 3));
        recycler_close_time.setAdapter(mAdapter_close);

        mAdapter_close.SetOnItemClickListener(new CloseTimeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DaysModelData model) {


            }
        });
    }

    public void getOpenDays(String DaysAll) {

        Log.e("DaysAll",DaysAll);
        Call<DaysModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_weekly_day(DaysAll);
        call.enqueue(new Callback<DaysModel>() {
            @Override
            public void onResponse(Call<DaysModel> call, Response<DaysModel> response) {

                try {
                  progressBar.setVisibility(View.GONE);
                    DaysModel finallyPr = response.body();

                    String status= finallyPr.getStatus().toString();
                    String message= finallyPr.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {

                        modelList_Opentime = (ArrayList<DaysModelData>) finallyPr.getResult();
                        modelList_closetime = (ArrayList<DaysModelData>) finallyPr.getResult();

                        setAdapterCloseTiime(modelList_closetime);

                        setAdapterOpenTime(modelList_Opentime);

                    } else {

                        Toast.makeText(OpenTimeCloseTimeActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DaysModel> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

            }
        });
    }


    public void AddTiming(){

        String UserId = Preference.get(OpenTimeCloseTimeActivity.this,Preference.KEY_USER_ID);

        HashMap<String, String> map = new HashMap<>();

        map.put("user_id",UserId);

        map.put("weekly_close",Weekly_closeDay);

        map.put("monday_open",Monday_open);
        map.put("tuesday_open",tuesDay_open);
        map.put("wednesday_open",wednesday_open);
        map.put("thursday_open",thursday_open);
        map.put("friday_open",Friday_open);
        map.put("saturday_open",saturday_open);
        map.put("sunday_open",sunday_open);

        map.put("monday_close",Monday_close);
        map.put("tuesday_close",Monday_close);
        map.put("wednesday_close",wednesday_close);
        map.put("thursday_close",thursday_close);
        map.put("friday_close",Friday_close);
        map.put("saturday_close",saturday_close);
        map.put("sunday_close",sunday_close);


        ApiRequest apiRequest = new ApiRequest(OpenTimeCloseTimeActivity.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_add_time_provider, Constants.USER_add_time_provider,map, Request.Method.POST);
    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_add_time_provider.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();
                    String result = jsonObject.getString("message").toString();

                    if(status.equalsIgnoreCase("1"))
                    {

                        //Intent intent=new Intent(AddTiming.this, SelectCategory.class);
                        Intent intent=new Intent(OpenTimeCloseTimeActivity.this, SelectCategory.class);
                        //Intent intent=new Intent(AddTiming.this, AddServicesActivity.class);
                        startActivity(intent);
                        finish();


                    }else
                    {

                        Toast.makeText(this, "Your have entered wrong email & password", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e) {

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


    private void validation() {

        if(Monday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Monday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(tuesDay_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Tuesday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(wednesday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Wednesday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(thursday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Thursday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(Friday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Friday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(saturday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Satirday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(sunday_open.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Sunday Open Time.", Toast.LENGTH_SHORT).show();

        }else if(Monday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Monday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(tuesDay_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Tuesday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(wednesday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Wednesday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(thursday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Wednesday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(Friday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select Friday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(saturday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select saturday Close Time.", Toast.LENGTH_SHORT).show();

        }else if(sunday_close.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select sunday Close Time.", Toast.LENGTH_SHORT).show();

        }else
        {
            if (preference.isNetworkAvailable()) {

                progressBar.setVisibility(View.VISIBLE);

                AddTiming();

            }else {

                Toast.makeText(OpenTimeCloseTimeActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

            }

        }
    }

}