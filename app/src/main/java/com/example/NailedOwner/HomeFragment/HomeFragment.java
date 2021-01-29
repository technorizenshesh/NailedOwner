package com.example.NailedOwner.HomeFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.NailedOwner.Accepting.AcceptingActivity;
import  com.example.NailedOwner.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    private Button btn_Login_accept;
    private View view;
    private Activity activity;
    Button btn_pending;
    Button btn_cancel;
    Button btn_complete;
    CircleImageView img_whats_up;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

      //  view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        view = inflater.inflate(R.layout.activity_accepting, container, false);

        findViews();

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

        return  view;
    }

    private void findViews() {
        btn_complete=view.findViewById(R.id.btn_complete);
        btn_cancel=view.findViewById(R.id.btn_cancel);
        btn_pending=view.findViewById(R.id.btn_pending);
        img_whats_up=view.findViewById(R.id.img_whats_up);
      //  btn_Login_accept=view.findViewById(R.id.btn_Login_accept);
    }


}
