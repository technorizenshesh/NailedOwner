package com.example.NailedOwner.MoreFragmet;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.NailedOwner.AddCertificate.AddyourCertificate;
import com.example.NailedOwner.AllSalonPhoto.AllSalonPhoto;
import com.example.NailedOwner.GetCertificate.GetCertificateActivity;
import com.example.NailedOwner.LoginScreen.LoginActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.PrivacyPolicy.PrivacyPolicy;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.TermsAndCondition;

import java.security.cert.Certificate;


public class MoreFragment extends Fragment {

    private View view;
    private Activity activity;
    private Button btn_Logout;
    private RelativeLayout RR_Services;
    private RelativeLayout RR_terms_condition;
    private RelativeLayout RR_privacy_policy;
    private RelativeLayout RR_getSaloonPic;

    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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

        view = inflater.inflate(R.layout.activity_more, container, false);

        findViews();

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.clearPreference(getActivity());
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        RR_getSaloonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), AllSalonPhoto.class);
                startActivity(intent);

            }
        });

        RR_Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), GetCertificateActivity.class);
                startActivity(intent);

            }
        });

        return  view;
    }

    private void findViews() {
        btn_Logout=view.findViewById(R.id.btn_Logout);
        RR_Services=view.findViewById(R.id.RR_Services);
        RR_getSaloonPic=view.findViewById(R.id.RR_getSaloonPic);
    }


}
