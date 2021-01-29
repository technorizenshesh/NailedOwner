package com.example.NailedOwner.AddTiming;


import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.NailedOwner.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class OpenTimeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<DaysModelData> modelList;
    private OnItemClickListener mItemClickListener;
    int mHour;
    int mMinute;



    String timeFinal ="Close";

    public OpenTimeRecyclerViewAdapter(Context context, ArrayList<DaysModelData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<DaysModelData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.close_timing, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {

            final DaysModelData model = getItem(position);

            final ViewHolder genericViewHolder = (ViewHolder) holder;

            String WeeklyClose =model.getWeeklyDay().toString();

            genericViewHolder.txt_name.setText(model.getName());

            if(WeeklyClose.equalsIgnoreCase("close"))
            {
                genericViewHolder.RR_monday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(mContext, model.getName()+" Already Selected in Weekly Off", Toast.LENGTH_SHORT).show();
                    }
                });
                genericViewHolder.txt_monday_open.setText(WeeklyClose);
            }else {

                genericViewHolder.RR_monday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        mHour = hourOfDay;
                                        mMinute = minute;

                                        Toast.makeText(mContext, ""+model.getName(), Toast.LENGTH_SHORT).show();

                                        genericViewHolder.txt_monday_open.setText(hourOfDay + ":" + minute+" AM");

                                        timeFinal = hourOfDay + ":" + minute+" AM";

                                         if(position == 0)
                                        {
                                            OpenTimeCloseTimeActivity.sunday_open =timeFinal;

                                        }else if(position == 1)
                                        {

                                            OpenTimeCloseTimeActivity.Monday_open =timeFinal;

                                        }else if(position == 2)
                                        {
                                            OpenTimeCloseTimeActivity.tuesDay_open =timeFinal;

                                        }else if(position == 3)
                                        {
                                            OpenTimeCloseTimeActivity.wednesday_open =timeFinal;

                                        }else if(position == 4)
                                        {

                                            OpenTimeCloseTimeActivity.thursday_open =timeFinal;

                                        }else if(position == 5)
                                        {
                                            OpenTimeCloseTimeActivity.Friday_open =timeFinal;

                                        }else if(position == 6)
                                        {
                                            OpenTimeCloseTimeActivity.saturday_open =timeFinal;

                                        }

                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                });

            }


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

    private DaysModelData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, DaysModelData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout RR_monday;
        private TextView txt_monday_open;
        private TextView txt_name;


        public ViewHolder(final View itemView) {
            super(itemView);

            this.RR_monday=itemView.findViewById(R.id.RR_monday);
            this.txt_monday_open=itemView.findViewById(R.id.txt_monday_open);
            this.txt_name=itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }



}

