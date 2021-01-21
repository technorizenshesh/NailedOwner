package com.example.NailedOwner.ProfileScreen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.NailedOwner.Accepting.AcceptingActivity;
import com.example.NailedOwner.ChangePassword.ChangePasswordActivity;
import com.example.NailedOwner.LoginScreen.LoginActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.PrivacyPolicy.PrivacyPolicy;
import com.example.NailedOwner.R;
import com.example.NailedOwner.TermsAndCondition;


public class ProfileFragment extends Fragment {

    private RelativeLayout RR_terms_condition;
    private RelativeLayout RR_privacy_policy;
    private RelativeLayout RR_ChangePassword;
    private TextView txt_userName;
    private TextView txt_userEmail;
    private View view;
    private Activity activity;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

        view = inflater.inflate(R.layout.activity_profile2, container, false);
        findViews();
        String UserName = Preference.get(getActivity(),Preference.KEY_USER_Name);
        String UserEmail =  Preference.get(getActivity(),Preference.KEY_USER_Email);
        String ImageUrl =  Preference.get(getActivity(),Preference.KEY_USER_mage);

        txt_userEmail.setText(UserName);
        txt_userName.setText(UserEmail);

        return  view;
    }

    private void findViews() {
        txt_userName=view.findViewById(R.id.txt_userName);
        txt_userEmail=view.findViewById(R.id.txt_userEmail);
        RR_terms_condition=view.findViewById(R.id.RR_terms_condition);
        RR_privacy_policy=view.findViewById(R.id.RR_privacy_policy);
        RR_ChangePassword=view.findViewById(R.id.RR_ChangePassword);
        RR_terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TermsAndCondition.class);
                startActivity(intent);
            }
        });

        RR_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


        RR_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });

    }


}
