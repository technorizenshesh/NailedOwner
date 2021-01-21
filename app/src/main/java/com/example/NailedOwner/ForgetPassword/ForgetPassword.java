package com.example.NailedOwner.ForgetPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.LoginScreen.LoginActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;
import  com.example.NailedOwner.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPassword extends AppCompatActivity implements IApiResponse {

    private RelativeLayout RR_back;
    private EditText edt_email;
    private Button btn_send;

    private ProgressBar progressBar;
    private Preference preference;
    String android_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

        findview();

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(this);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();

            }
        });
    }

    private void findview() {
        RR_back=findViewById(R.id.RR_back);
        edt_email=findViewById(R.id.edt_email);
        btn_send=findViewById(R.id.btn_send);
        progressBar=findViewById(R.id.progressBar);
    }

    private void validation() {

        String email=edt_email.getText().toString();

        if(!isValidEmail(email))
        {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Please Enter Email.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }else
        {
            if (preference.isNetworkAvailable()) {

                btn_send.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                forgetMethod(email);

            }else {

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void forgetMethod(String email){

        HashMap<String, String> map = new HashMap<>();

        map.put("email",email);

        ApiRequest apiRequest = new ApiRequest(ForgetPassword.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_ForgetPassword, Constants.USER_ForgetPassword,map, Request.Method.POST);
    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_ForgetPassword.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

            btn_send.setEnabled(true);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();
                    String result = jsonObject.getString("result").toString();

                    if(status.equalsIgnoreCase("1"))
                    {
                        ForgetModel finalArray = new Gson().fromJson(response,new TypeToken<ForgetModel>(){}.getType());

                        Toast.makeText(this, finalArray.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(ForgetPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else
                    {
                        btn_send.setEnabled(true);
                        Toast.makeText(this, "Your have entered wrong email & password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    btn_send.setEnabled(true);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        btn_send.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
    }
}
