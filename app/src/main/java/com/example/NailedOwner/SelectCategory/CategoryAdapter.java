package com.example.NailedOwner.SelectCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import  com.example.NailedOwner.R;

import java.util.ArrayList;


public class CategoryAdapter extends BaseAdapter  {
    Context context;
    ArrayList<ChoseCategoryModel> OccationList;
    LayoutInflater inflter;

    public CategoryAdapter(Context applicationContext, ArrayList<ChoseCategoryModel> OccationList) {
        this.context = applicationContext;
        this.OccationList = OccationList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return OccationList.size();
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
        view = inflter.inflate(R.layout.chose_category, null);
      //  ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        //icon.setImageResource(flags[i]);
       names.setText(OccationList.get(i).getCategoryName());
        return view;
    }
}