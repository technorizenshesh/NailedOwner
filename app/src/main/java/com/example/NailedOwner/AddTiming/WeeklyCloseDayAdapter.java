package com.example.NailedOwner.AddTiming;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import  com.example.NailedOwner.R;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class WeeklyCloseDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<OpenTimingModel> modelList;
    private OnItemClickListener mItemClickListener;
    String c="";

    public WeeklyCloseDayAdapter(Context context, ArrayList<OpenTimingModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<OpenTimingModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.open_timing, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {

            final OpenTimingModel model = getItem(position);

            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txt_time.setText(model.getTime());

            genericViewHolder.RR_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    model.setSelected(!model.isSelected());

                    if(model.isSelected())
                    {
                        genericViewHolder.RR_item.setBackgroundResource(R.drawable.whiteeditround_new);

                    }else
                    {
                        genericViewHolder.RR_item.setBackgroundResource(R.drawable.whiteeditround);
                    }

                   // genericViewHolder.RR_item.setBackgroundResource(model.isSelected() ? R.drawable.whiteeditround : R.drawable.whiteeditround_new);
                }
            });

           // genericViewHolder.txt_time_one.setText(model.getTie_one());
        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

   public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private OpenTimingModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, OpenTimingModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txt_time;
        private TextView txt_time_one;
        private RelativeLayout RR_item;




        public ViewHolder(final View itemView) {
            super(itemView);

            this.txt_time=itemView.findViewById(R.id.txt_time);
            this.txt_time_one=itemView.findViewById(R.id.txt_time_one);
            this.RR_item=itemView.findViewById(R.id.RR_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }



}

