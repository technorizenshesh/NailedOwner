package com.example.NailedOwner.HomeScree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.NailedOwner.AllServices.AllServices;
import com.example.NailedOwner.DashBoard.DashBoard;
import com.example.NailedOwner.HomeFragment.HomeFragment;
import com.example.NailedOwner.MoreFragmet.MoreFragment;
import com.example.NailedOwner.ProfileScreen.ProfileFragment;
import  com.example.NailedOwner.R;

public class HomeActivity extends AppCompatActivity {

    private Button btn_Login_accept;

    private RelativeLayout RR_home;
    private RelativeLayout RR_Services;
    private RelativeLayout RR_Dashboard;
    private RelativeLayout RR_Profile;
    private RelativeLayout RR_more;

    private ImageView img_home;
    private ImageView img_services;
    private ImageView img_Dashboard;
    private ImageView img_Profile;
    private ImageView img_more;

    private TextView txt_Home;
    private TextView txt_services;
    private TextView txt_Dashboard;
    private TextView txt_profile;
    private TextView txt_more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

      //  btn_Login_accept=findViewById(R.id.btn_Login_accept);
        RR_home=findViewById(R.id.RR_home);
        RR_Services=findViewById(R.id.RR_Services);
        RR_Dashboard=findViewById(R.id.RR_Dashboard);
        RR_Profile=findViewById(R.id.RR_Profile);
        RR_more=findViewById(R.id.RR_more);

        img_home=findViewById(R.id.img_home);
        img_services=findViewById(R.id.img_services);
        img_Dashboard=findViewById(R.id.img_Dashboard);
        img_Profile=findViewById(R.id.img_Profile);
        img_more=findViewById(R.id.img_more);

        txt_Home=findViewById(R.id.txt_Home);
        txt_services=findViewById(R.id.txt_services);
        txt_Dashboard=findViewById(R.id.txt_Dashboard);
        txt_profile=findViewById(R.id.txt_profile);
        txt_more=findViewById(R.id.txt_more);


        RR_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();


                img_home.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_services.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Dashboard.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Profile.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_more.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);

                txt_Home.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one));
                txt_services.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_Dashboard.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_profile.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_more.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));


            }
        });

        RR_Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AllServices()).commit();

                img_home.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_services.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Dashboard.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Profile.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_more.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);

                txt_Home.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_services.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one));
                txt_Dashboard.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_profile.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_more.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));

            }
        });

        RR_Dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new DashBoard()).commit();

                img_home.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_services.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Dashboard.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Profile.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_more.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);


                txt_Home.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_services.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_Dashboard.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one));
                txt_profile.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_more.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
            }
        });
        RR_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ProfileFragment()).commit();

                img_home.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_services.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Dashboard.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Profile.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_more.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);


                txt_Home.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_services.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_Dashboard.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_profile.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one));
                txt_more.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
            }
        });

        RR_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_home.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_services.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Dashboard.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Profile.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_more.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one), android.graphics.PorterDuff.Mode.MULTIPLY);

                txt_Home.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_services.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_Dashboard.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_profile.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                txt_more.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.mehroon_one));

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new MoreFragment()).commit();

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();

       /* btn_Login_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this, AcceptingActivity.class);
                startActivity(intent);
            }
        });*/

    }
}
