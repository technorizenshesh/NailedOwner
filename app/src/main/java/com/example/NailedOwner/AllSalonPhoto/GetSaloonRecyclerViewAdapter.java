package com.example.NailedOwner.AllSalonPhoto;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NailedOwner.AddServicesTwo.GetServicesDataModel;
import com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class GetSaloonRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Button btn_no;
    private Button btn_yes;
    private Context mContext;
    private AlertDialog alertDialog;
    private ArrayList<GetImageSaloonData> modelList;
    private OnItemClickListener mItemClickListener;
    private View promptsView;

    public GetSaloonRecyclerViewAdapter(Context context, ArrayList<GetImageSaloonData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetImageSaloonData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_get_saloon_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetImageSaloonData model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetImageSaloonData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetImageSaloonData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_saloon;

        public ViewHolder(final View itemView) {
            super(itemView);

      this.img_saloon=itemView.findViewById(R.id.img_saloon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

