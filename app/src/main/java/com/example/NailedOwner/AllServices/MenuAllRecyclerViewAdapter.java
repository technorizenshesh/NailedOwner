package com.example.NailedOwner.AllServices;


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
public class MenuAllRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Button btn_no;
    private Button btn_yes;
    private Context mContext;
    private AlertDialog alertDialog;
    private ArrayList<GetServicesDataModel> modelList;
    private OnItemClickListener mItemClickListener;
    private View promptsView;

    public MenuAllRecyclerViewAdapter(Context context, ArrayList<GetServicesDataModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetServicesDataModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu_service, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetServicesDataModel model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

        //    genericViewHolder.text_1.setText(model.getHome().getAddress().toString()+" - "+model.getStartTime() + " - "+model.getEndTime());

          genericViewHolder.txt_name.setText(model.getName().toString());
          genericViewHolder.txt_price.setText("$"+model.getPrice().toString());
          genericViewHolder.txt_minute.setText(model.getServiceDuration()+" min");

          genericViewHolder.RR_remove.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  modelList.remove(position);
                  notifyDataSetChanged();
                  getServicesListMethod(model.getId().toString());

              }
          });


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetServicesDataModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetServicesDataModel model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout RR_remove;
        private TextView txt_name;
        private TextView txt_price;
        private TextView txt_minute;
        private ImageView img_complete;

        public ViewHolder(final View itemView) {
            super(itemView);

      this.txt_name=itemView.findViewById(R.id.txt_name);
      this.txt_price=itemView.findViewById(R.id.txt_price);
      this.RR_remove=itemView.findViewById(R.id.RR_remove);
      this.txt_minute=itemView.findViewById(R.id.txt_minute);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }

    private void getServicesListMethod(String service_id){

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .delete_service(service_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

