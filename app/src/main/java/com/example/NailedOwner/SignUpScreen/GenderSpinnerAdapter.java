package com.example.NailedOwner.SignUpScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.NailedOwner.R;

public class GenderSpinnerAdapter extends BaseAdapter {

    Context context;
    int flags[];
    String code[];
    String[] countryNames;
    LayoutInflater inflter;
    TextView countrycode;
    RelativeLayout rll_spinner;
    ImageView icon;

    public GenderSpinnerAdapter(Context applicationContext, int[] flags, String[] code) {
        this.context = applicationContext;
        this.flags = flags;
        this.code = code;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return code.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_layout_gender, null);
        //icon = (ImageView) view.findViewById(R.id.img_flag);
       countrycode = (TextView) view.findViewById(R.id.textview);

        countrycode.setText(code[i]);

        return view;

    }
}

