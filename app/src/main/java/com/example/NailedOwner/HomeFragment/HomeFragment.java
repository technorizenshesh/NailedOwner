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


public class HomeFragment extends Fragment {

    private Button btn_Login_accept;
    private View view;
    private Activity activity;

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

        view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        findViews();

     btn_Login_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), AcceptingActivity.class);
                startActivity(intent);
            }
        });


        return  view;
    }

    private void findViews() {
        btn_Login_accept=view.findViewById(R.id.btn_Login_accept);
    }


}
