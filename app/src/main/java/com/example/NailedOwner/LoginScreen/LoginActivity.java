package com.example.NailedOwner.LoginScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.AddCertificate.AddyourCertificate;
import com.example.NailedOwner.AddServices.AddServicesActivity;
import com.example.NailedOwner.AddTiming.AddTiming;
import com.example.NailedOwner.GPSTracker;
import com.example.NailedOwner.HomeScree.HomeActivity;
import com.example.NailedOwner.SignUpScreen.SignUpActivity;
import com.example.NailedOwner.SignUpScreen.SignUpModel;
import com.example.NailedOwner.AddSaloonPhoto.AddsaloonPhotos;
import com.example.NailedOwner.ForgetPassword.ForgetPassword;
import com.example.NailedOwner.Preference;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.SelectCategory.SelectCategory;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,IApiResponse {

    private Spinner spinnerCountry;
    private int code[] ={+1};
    private int flags[]= {R.mipmap.flag,R.drawable.usa_flag,R.drawable.china,R.drawable.indonesia};
    private Button btn_Login;
    private TextView txt_forgot_password;
    private TextView txt_sign_in;

    private EditText edt_mobile;
    private EditText edt_password;
    private ImageView img_password;

    private Preference preference;
    private ProgressBar progressBar;
    String android_id ="";

    //Google SignIn
    private RelativeLayout RR_google_login;
    private SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    GPSTracker gpsTracker;
    String latitude ="";
    String longitude ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        CountrySpinnerAdapter customAdapter=new CountrySpinnerAdapter(LoginActivity.this,flags,code);
        spinnerCountry.setAdapter(customAdapter);
        spinnerCountry.setEnabled(true);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);

            }
        });

        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        img_password.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });

        RR_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);

            }
        });

        //Google SignIn
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void findview() {
        RR_google_login=findViewById(R.id.RR_google_login);
        progressBar=findViewById(R.id.progressBar);
        spinnerCountry=(Spinner)findViewById(R.id.spinnerCountry);
        btn_Login=(Button)findViewById(R.id.btn_Login);
        txt_forgot_password=findViewById(R.id.txt_forgot_password);
        txt_sign_in=findViewById(R.id.txt_sign_in);
        edt_mobile=findViewById(R.id.edt_mobile);
        edt_password=findViewById(R.id.edt_password);
        img_password=findViewById(R.id.img_password);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker=new GPSTracker(this);

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void validation() {

        String mobile=edt_mobile.getText().toString();
        String password=edt_password.getText().toString();

        if(mobile.equalsIgnoreCase(""))
        {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Please Enter Mobile Number.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }else if(password.equalsIgnoreCase(""))
        {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Please Enter password.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }else
        {
            if (preference.isNetworkAvailable()) {

                btn_Login.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                loginMethod(mobile,password);

            }else {

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }


 /*   public static boolean isValidEmail(CharSequence target) {
       // return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }*/


    public void SocialLogin(String user_name,String mobile,String email,String social_id,String image){

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name",user_name);
        map.put("mobile",mobile);
        map.put("email",email);
        map.put("social_id",social_id);
        map.put("lat",latitude);
        map.put("lon",longitude);
        map.put("image","xyz");
        map.put("register_id",android_id);

        ApiRequest apiRequest = new ApiRequest(LoginActivity.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_social_login, Constants.USER_social_login,map, Request.Method.POST);
    }

    public void loginMethod(String mobile,String password){

        HashMap<String, String> map = new HashMap<>();

        map.put("mobile",mobile);
        map.put("password",password);
        map.put("register_id",android_id);

        ApiRequest apiRequest = new ApiRequest(LoginActivity.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_LOGIN, Constants.USER_LOGIN,map, Request.Method.POST);
    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_LOGIN.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

            btn_Login.setEnabled(true);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();
                    String result = jsonObject.getString("message").toString();

                    if(status.equalsIgnoreCase("1"))
                    {
                        SignUpModel finalArray = new Gson().fromJson(response,new TypeToken<SignUpModel>(){}.getType());

                        Toast.makeText(this, finalArray.getResult().getId(), Toast.LENGTH_SHORT).show();

                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finalArray.getResult().getId());
                        Preference.save(LoginActivity.this,Preference.KEY_USER_Name,finalArray.getResult().getUserName());
                        Preference.save(LoginActivity.this,Preference.KEY_USER_Email,finalArray.getResult().getEmail());
                        Preference.save(LoginActivity.this,Preference.KEY_USER_mage,finalArray.getResult().getImage());

                        if(finalArray.getResult().getScreenCount().equalsIgnoreCase("0"))
                        {
                            Intent intent=new Intent(LoginActivity.this, AddsaloonPhotos.class);
                            startActivity(intent);
                            finish();

                        }else if(finalArray.getResult().getScreenCount().equalsIgnoreCase("1"))
                        {
                            Intent intent=new Intent(LoginActivity.this, AddTiming.class);
                            startActivity(intent);
                            finish();

                        }else if(finalArray.getResult().getScreenCount().equalsIgnoreCase("2"))
                        {
                            Intent intent=new Intent(LoginActivity.this, SelectCategory.class);
                            startActivity(intent);
                            finish();

                        }else if(finalArray.getResult().getScreenCount().equalsIgnoreCase("3"))
                        {
                            Intent intent=new Intent(LoginActivity.this, AddServicesActivity.class);
                            startActivity(intent);
                            finish();

                        }else if(finalArray.getResult().getScreenCount().equalsIgnoreCase("4"))
                        {
                            Intent intent=new Intent(LoginActivity.this, AddyourCertificate.class);
                            startActivity(intent);
                            finish();

                        }else if(finalArray.getResult().getScreenCount().equalsIgnoreCase("5"))
                        {
                            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }else
                    {
                        btn_Login.setEnabled(true);
                        Toast.makeText(this, "Your have entered wrong email & password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    btn_Login.setEnabled(true);
                    e.printStackTrace();
                }
            }
        }else   if (Constants.USER_social_login.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

           // signInButton.setEnabled(true);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();
                    String result = jsonObject.getString("message").toString();

                    if(status.equalsIgnoreCase("1"))
                    {
                        SignUpModel finalArray = new Gson().fromJson(response,new TypeToken<SignUpModel>(){}.getType());

                        Toast.makeText(this, finalArray.getMessage(), Toast.LENGTH_SHORT).show();

                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finalArray.getResult().getId());

                        Intent intent=new Intent(LoginActivity.this, AddsaloonPhotos.class);
                        startActivity(intent);
                        finish();

                       /* Intent intent=new Intent(LoginActivity.this, SelectCategory.class);
                        startActivity(intent);
                        finish();*/

                    }else
                    {
                     //   signInButton.setEnabled(true);
                        Toast.makeText(this, "Your have entered wrong email & password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //signInButton.setEnabled(true);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        btn_Login.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
    }


    //Google Login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            handleSignInResult( result );
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            String UsernAME=account.getDisplayName();
            String email=account.getEmail();
            String SocialId=account.getId();
            Uri Url=account.getPhotoUrl();

          //  Toast.makeText( this, "Login successful", Toast.LENGTH_SHORT ).show();

            if (preference.isNetworkAvailable()) {

//                signInButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                SocialLogin(UsernAME,"123456789",email,SocialId, String.valueOf(Url));

            }else {

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText( this, "Login Unsuccessful", Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

