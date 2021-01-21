package com.example.NailedOwner.AddTiming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.Preference;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.SelectCategory.SelectCategory;
import com.example.NailedOwner.Utills.RetrofitClients;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTiming extends AppCompatActivity implements IApiResponse {

    private RelativeLayout RR_monday_time;
    private RelativeLayout RR_tuesday_time;
    private RelativeLayout RR_wednesday_time;
    private RelativeLayout RR_thursday_time;
    private RelativeLayout RR_friday_time;
    private RelativeLayout RR_saturday_time;
    private RelativeLayout RR_sunday_time;

    private Button btn_next_timing;
    private RecyclerView recycler_weekly_close;
    private RecyclerView recycler_open_time;
    private RecyclerView recycler_close_time;
    private WeeklyCloseDayAdapter mAdapter;
    private CloseTimeRecyclerViewAdapter  mAdapter_close;
    private ArrayList<OpenTimingModel> modelList = new ArrayList<>();
    private ArrayList<OpenTimingModel> modelList_closetime = new ArrayList<>();
    private ArrayList<OpenTimingModel> modelList_Opentime = new ArrayList<>();

    private Preference preference;
    private ProgressBar progressBar;
    String android_id ="";
    String Day="";
    String OpenTime="";
    String CloseTime="";

  private RelativeLayout RR_monday;
  private RelativeLayout RR_tuesday;
  private RelativeLayout RR_wednesday;
  private RelativeLayout RR_thursday;
  private RelativeLayout RR_Friday;
  private RelativeLayout RR_saturday;
  private RelativeLayout RR_sunday;

    private RelativeLayout RR_monday_close;
    private RelativeLayout RR_tuesday_close;
    private RelativeLayout RR_wednesday_close;
    private RelativeLayout RR_thursday_close;
    private RelativeLayout RR_Friday_close;
    private RelativeLayout RR_saturday_close;
    private RelativeLayout RR_sunday_close;

    int mHour;
    int mMinute;
    private TextView txt_monday_openl;
    private TextView txt_tuesday_open;
    private TextView txt_wednesday_open;
    private TextView txt_thursday_open;
    private TextView txt_friday_open;
    private TextView txt_saturday_open;
    private TextView txt_sunday_open;

    private TextView txt_Monday_close_one;
    private TextView txt_tuesday_close_open;
    private TextView txt_wednesday_close_open;
    private TextView txt_thursday_close_open;
    private TextView txt_Friday_close_open;
    private TextView txt_saturday_close_open;
    private TextView txt_sunday_close_open;

    String Weekly_closeDay ="";

    String Monday_open ="";
    String tuesDay_open ="";
    String wednesday_open ="";
    String thursday_open ="";
    String Friday_open ="";
    String saturday_open ="";
    String sunday_open ="";

    String Monday_close ="";
    String tuesDay_close ="";
    String wednesday_close ="";
    String thursday_close ="";
    String Friday_close ="";
    String saturday_close ="";
    String sunday_close ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timing);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }
        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(this);

        findview();

        btn_next_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1="";
                String s="";
                for(int i=0;i<modelList.size();i++)
                {
                    if(modelList.get(i).isSelected())
                    {
                        s = modelList.get(i).getTime();
                        s1 =s1+s+",";
                    }
                }

                Weekly_closeDay = s1;

                if (Weekly_closeDay.endsWith(",")) {
                    Weekly_closeDay = Weekly_closeDay.substring(0, Weekly_closeDay.length() - 1);
                }

              //  Weekly_closeDay = (Weekly_closeDay.replaceAll(",",""));

                Toast.makeText(AddTiming.this, ""+Weekly_closeDay, Toast.LENGTH_SHORT).show();

                    //     validation();

            }
        });

        RR_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_monday_openl.setText(hourOfDay + ":" + minute+" AM");

                                Monday_open = hourOfDay + ":" + minute+" AM";

                                Toast.makeText(AddTiming.this, Monday_open, Toast.LENGTH_SHORT).show();

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_tuesday_open.setText(hourOfDay + ":" + minute+" AM");

                                tuesDay_open = hourOfDay + ":" + minute+" AM" ;

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_wednesday_open.setText(hourOfDay + ":" + minute+" AM");

                               wednesday_open = hourOfDay + ":" + minute+" AM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_thursday_open.setText(hourOfDay + ":" + minute+" AM");

                               thursday_open = hourOfDay + ":" + minute+" AM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_friday_open.setText(hourOfDay + ":" + minute+" AM");

                                Friday_open = hourOfDay + ":" + minute+" AM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_saturday_open.setText(hourOfDay + ":" + minute+" AM");

                                saturday_open = hourOfDay + ":" + minute+" AM";

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        });

        RR_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_sunday_open.setText(hourOfDay + ":" + minute+" AM");

                                sunday_open = hourOfDay + ":" + minute+" AM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_monday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_Monday_close_one.setText(hourOfDay + ":" + minute+" PM");

                                Monday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);

                timePickerDialog.show();

            }
        });

        RR_tuesday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_tuesday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                tuesDay_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_wednesday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_wednesday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                wednesday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_thursday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_thursday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                thursday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_Friday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_Friday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                Friday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        RR_saturday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_saturday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                saturday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        RR_sunday_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTiming.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                txt_sunday_close_open.setText(hourOfDay + ":" + minute+" PM");

                                sunday_close = hourOfDay + ":" + minute+" PM";

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        setAdapterWeeklyClose();
        setAdapterCloseTiime();
        setAdapterOpenTime();
    }


    private void findview() {

        txt_Monday_close_one=findViewById(R.id.txt_Monday_close_one);
        txt_tuesday_close_open=findViewById(R.id.txt_tuesday_close_open);
        txt_wednesday_close_open=findViewById(R.id.txt_wednesday_close_open);
        txt_thursday_close_open=findViewById(R.id.txt_thursday_close_open);
        txt_Friday_close_open=findViewById(R.id.txt_Friday_close_open);
        txt_saturday_close_open=findViewById(R.id.txt_saturday_close_open);
        txt_sunday_close_open=findViewById(R.id.txt_sunday_close_open);

        txt_monday_openl=findViewById(R.id.txt_monday_open);
        txt_tuesday_open=findViewById(R.id.txt_tuesday_open);
        txt_wednesday_open=findViewById(R.id.txt_wednesday_open);
        txt_thursday_open=findViewById(R.id.txt_thursday_open);
        txt_friday_open=findViewById(R.id.txt_friday_open);
        txt_saturday_open=findViewById(R.id.txt_saturday_open);
        txt_sunday_open=findViewById(R.id.txt_sunday_open);
         RR_monday=findViewById(R.id.RR_monday);
        RR_tuesday=findViewById(R.id.RR_tuesday);
       RR_wednesday=findViewById(R.id.RR_wednesday);
        RR_thursday=findViewById(R.id.RR_thursday);
        RR_Friday=findViewById(R.id.RR_Friday);
        RR_saturday=findViewById(R.id.RR_saturday);
       RR_sunday=findViewById(R.id.RR_sunday);

       RR_monday_close=findViewById(R.id.RR_monday_close);
        RR_tuesday_close=findViewById(R.id.RR_tuesday_close);
       RR_wednesday_close=findViewById(R.id.RR_wednesday_close);
        RR_thursday_close=findViewById(R.id.RR_thursday_close);
        RR_Friday_close=findViewById(R.id.RR_Friday_close);
        RR_saturday_close=findViewById(R.id.RR_saturday_close);
       RR_sunday_close=findViewById(R.id.RR_sunday_close);

        btn_next_timing=findViewById(R.id.btn_next_timing);
        recycler_weekly_close=findViewById(R.id.recycler_weekly_close);
        recycler_close_time=findViewById(R.id.recycler_close_time);
        recycler_open_time=findViewById(R.id.recycler_open_time);
        progressBar=findViewById(R.id.progressBar);

    }

    private void validation() {

        if(Weekly_closeDay.equalsIgnoreCase(""))
        {

            Toast.makeText(this, "Please Select Weekly Close Day.", Toast.LENGTH_SHORT).show();

        }else if(Monday_open.equalsIgnoreCase(""))
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

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void setAdapterWeeklyClose() {

        modelList.add(new OpenTimingModel("Monday","AM"));
        modelList.add(new OpenTimingModel("Tuesday","AM"));
        modelList.add(new OpenTimingModel("Wednesday","AM"));
        modelList.add(new OpenTimingModel("Thursday","AM"));
        modelList.add(new OpenTimingModel("Friday","AM"));
        modelList.add(new OpenTimingModel("Saturday","AM"));
        modelList.add(new OpenTimingModel("Sunday","AM"));

        mAdapter = new WeeklyCloseDayAdapter(AddTiming.this, this.modelList);
        recycler_weekly_close.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddTiming.this);
        recycler_weekly_close.setLayoutManager(new GridLayoutManager(AddTiming.this, 3));
        recycler_weekly_close.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new WeeklyCloseDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, OpenTimingModel model) {

            }
        });
    }

    private void setAdapterCloseTiime() {

        modelList_closetime.add(new OpenTimingModel("Monday","AM"));
        modelList_closetime.add(new OpenTimingModel("Tuesday","AM"));
        modelList_closetime.add(new OpenTimingModel("Wednesday","AM"));
        modelList_closetime.add(new OpenTimingModel("Thursday","AM"));
        modelList_closetime.add(new OpenTimingModel("Friday","AM"));
        modelList_closetime.add(new OpenTimingModel("Saturday","AM"));
        modelList_closetime.add(new OpenTimingModel("Sunday","AM"));


        mAdapter_close = new CloseTimeRecyclerViewAdapter(AddTiming.this, this.modelList_closetime);
        recycler_close_time.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddTiming.this);
        recycler_close_time.setLayoutManager(new GridLayoutManager(AddTiming.this, 3));
        recycler_close_time.setAdapter(mAdapter_close);

        mAdapter_close.SetOnItemClickListener(new CloseTimeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, OpenTimingModel model) {


            }
        });
    }

    private void setAdapterOpenTime() {

        modelList_Opentime.add(new OpenTimingModel("Monday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Tuesday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Wednesday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Thursday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Friday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Saturday","AM"));
        modelList_Opentime.add(new OpenTimingModel("Sunday","AM"));


        mAdapter_close = new CloseTimeRecyclerViewAdapter(AddTiming.this, this.modelList_Opentime);
        recycler_open_time.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddTiming.this);
        recycler_open_time.setLayoutManager(new GridLayoutManager(AddTiming.this, 3));
        recycler_open_time.setAdapter(mAdapter_close);

        mAdapter_close.SetOnItemClickListener(new CloseTimeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, OpenTimingModel model) {


            }
        });
    }

    public void AddTiming(){

        String UserId = Preference.get(AddTiming.this,Preference.KEY_USER_ID);

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
        map.put("tuesday_close",tuesDay_close);
        map.put("wednesday_close",wednesday_close);
        map.put("thursday_close",thursday_close);
        map.put("friday_close",Friday_close);
        map.put("saturday_close",saturday_close);
        map.put("sunday_close",sunday_close);


        ApiRequest apiRequest = new ApiRequest(AddTiming.this,this);

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
                        Intent intent=new Intent(AddTiming.this, SelectCategory.class);
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

    public void getAllDays() {

        Call<DaysModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_day();

        call.enqueue(new Callback<DaysModel>() {
            @Override
            public void onResponse(Call<DaysModel> call, Response<DaysModel> response) {

                try {
                    progressBar.setVisibility(View.GONE);
                    DaysModel finallyPr = response.body();

                    String status= finallyPr.getStatus().toString();
                    String message= finallyPr.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {

//                          modelList=(finallyPr.getResult())
                    } else {


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
}
