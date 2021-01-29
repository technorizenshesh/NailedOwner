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
import android.util.Log;
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

public class AddTiming extends AppCompatActivity {

    private RelativeLayout RR_monday_time;
    private RelativeLayout RR_tuesday_time;
    private RelativeLayout RR_wednesday_time;
    private RelativeLayout RR_thursday_time;
    private RelativeLayout RR_friday_time;
    private RelativeLayout RR_saturday_time;
    private RelativeLayout RR_sunday_time;

    private Button btn_next_timing;
    private RecyclerView recycler_weekly_close;
    private WeeklyCloseDayAdapter mAdapter;
    private ArrayList<DaysModelData> modelList = new ArrayList<>();

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
                        s =modelList.get(i).getName();
                        s1 =s1+s+",";
                    }
                }

                Weekly_closeDay = s1;

                if (Weekly_closeDay.endsWith(",")) {
                    Weekly_closeDay = Weekly_closeDay.substring(0, Weekly_closeDay.length() - 1);
                }

                Intent intent=new Intent(AddTiming.this,OpenTimeCloseTimeActivity.class);
                intent.putExtra("Weekly_closeDay",Weekly_closeDay);
                startActivity(intent);

                Toast.makeText(AddTiming.this, ""+Weekly_closeDay, Toast.LENGTH_SHORT).show();

                        //validation();

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



        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getAllDaysApi();

        }else {
            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
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

        progressBar=findViewById(R.id.progressBar);

    }




    private void setAdapterWeeklyClose(ArrayList<DaysModelData> modelList) {

        mAdapter = new WeeklyCloseDayAdapter(AddTiming.this, modelList);
        recycler_weekly_close.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddTiming.this);
        recycler_weekly_close.setLayoutManager(new GridLayoutManager(AddTiming.this, 3));
        recycler_weekly_close.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new WeeklyCloseDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DaysModelData model) {


            }
        });
    }




    public void getAllDaysApi() {

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

                     modelList= (ArrayList<DaysModelData>) finallyPr.getResult();

                        setAdapterWeeklyClose(modelList);

                    } else {

                        Toast.makeText(AddTiming.this, ""+message, Toast.LENGTH_SHORT).show();

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
