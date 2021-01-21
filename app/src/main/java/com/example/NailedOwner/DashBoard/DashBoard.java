package com.example.NailedOwner.DashBoard;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import  com.example.NailedOwner.R;


public class DashBoard extends Fragment {

    private View view;
    private Activity activity;

    public static DashBoard newInstance(String param1, String param2) {
        DashBoard fragment = new DashBoard();
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

        view = inflater.inflate(R.layout.activity_nailed, container, false);

        findViews();

        return  view;
    }

    private void findViews() {

    }


}
