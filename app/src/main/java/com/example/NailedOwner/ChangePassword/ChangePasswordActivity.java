package com.example.NailedOwner.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.NailedOwner.AddServicesTwo.AddServicesTwo;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private RelativeLayout RR_back;
    private Button btn_save;
    private EditText edt_newPassword;
    private EditText edt_Confirm_password;
    private ProgressBar progressBar;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        preference = new Preference(this);
        RR_back=findViewById(R.id.RR_back);
        edt_newPassword=findViewById(R.id.edt_newPassword);
        edt_Confirm_password=findViewById(R.id.edt_Confirm_password);
        btn_save=findViewById(R.id.btn_save);
        progressBar=findViewById(R.id.progressBar);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Password =edt_newPassword.getText().toString();
                String Confirm_password =edt_Confirm_password.getText().toString();

                if(Password.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter new password", Toast.LENGTH_SHORT).show();

                }else if(Confirm_password.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter new Confirm password", Toast.LENGTH_SHORT).show();

                }else if(!Confirm_password.equalsIgnoreCase(Password))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Don't match password.", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (preference.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);
                        ChangePassword(Confirm_password);

                    }else {

                        Toast.makeText(ChangePasswordActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

    }

    private void ChangePassword(String password){

        String UserId = Preference.get(ChangePasswordActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .change_password(UserId,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message =  jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                        finish();

                    } else {

                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}