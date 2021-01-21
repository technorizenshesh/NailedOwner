package com.example.NailedOwner.Accepting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.NailedOwner.Menu.MenuActivity;
import com.example.NailedOwner.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcceptingActivity extends AppCompatActivity {


    RelativeLayout RR_back;
    RelativeLayout LL_txt;
    Button btn_pending;
    Button btn_cancel;
    Button btn_complete;
    CircleImageView img_whats_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepting);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

        btn_complete=findViewById(R.id.btn_complete);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_pending=findViewById(R.id.btn_pending);
        RR_back=findViewById(R.id.RR_back);
        LL_txt=findViewById(R.id.LL_txt);
        img_whats_up=findViewById(R.id.img_whats_up);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LL_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent=new Intent(AcceptingActivity.this, MenuActivity.class);
                startActivity(intent);*/
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pending.setBackgroundResource(R.drawable.roundbttn_white);
                btn_cancel.setBackgroundResource(R.drawable.roundbttn_white);
                btn_complete.setBackgroundResource(R.drawable.roundbttn_register);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pending.setBackgroundResource(R.drawable.roundbttn_white);
                btn_cancel.setBackgroundResource(R.drawable.roundbttn_register);
                btn_complete.setBackgroundResource(R.drawable.roundbttn_white);
            }
        });

        btn_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pending.setBackgroundResource(R.drawable.roundbttn_register);
                btn_cancel.setBackgroundResource(R.drawable.roundbttn_white);
                btn_complete.setBackgroundResource(R.drawable.roundbttn_white);
            }
        });

        img_whats_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                // Put this line here
                sendIntent.setPackage("com.whatsapp");
                //
                startActivity(sendIntent);

            }
        });

    }
}
