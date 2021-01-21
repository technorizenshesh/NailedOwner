package com.example.NailedOwner.AllServices;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NailedOwner.AddServices.AddServicesActivity;
import com.example.NailedOwner.AddServicesTwo.GetServicesDataModel;
import com.example.NailedOwner.AddServicesTwo.GetServicesModel;
import com.example.NailedOwner.AddServicesTwo.GetServicesRecyclerViewAdapter;
import com.example.NailedOwner.AllServices.UpdateServices.UpdateServices;
import com.example.NailedOwner.Preference;
import  com.example.NailedOwner.R;
import com.example.NailedOwner.Utills.RetrofitClients;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllServices extends Fragment {

    private View view;

    private Button btn_next_services;
    private ImageView add_services;
    private Preference preference;
    private ProgressBar progressBar;
    String android_id ="";

    private RecyclerView recycler_getServces;
    private MenuAllRecyclerViewAdapter mAdapter;
    private ArrayList<GetServicesDataModel> modelList = new ArrayList<>();

    public static AllServices newInstance(String param1, String param2) {
        AllServices fragment = new AllServices();
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

        view = inflater.inflate(R.layout.activity_add_services_two_one, container, false);


        progressBar=view.findViewById(R.id.progressBar);
        recycler_getServces=view.findViewById(R.id.recycler_getServces);
        add_services=view.findViewById(R.id.add_services);

        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(getActivity());

        add_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),AddMenuServices.class);
                startActivity(intent);
            }
        });


        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getServicesListMethod();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }

        return  view;

    }

    private void setAdapter(ArrayList<GetServicesDataModel> modelList) {

        mAdapter = new MenuAllRecyclerViewAdapter(getActivity(), this.modelList);
        recycler_getServces.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_getServces.setLayoutManager(linearLayoutManager);
        recycler_getServces.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MenuAllRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetServicesDataModel model) {

                Preference.save(getActivity(), Preference.KEY_Services_id,model.getId());

                Intent intent=new Intent(getActivity(),UpdateServices.class);
                startActivity(intent);
            }
        });
    }

    private void getServicesListMethod(){

        String UserId = Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<GetServicesModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .USER_get_service(UserId);

        call.enqueue(new Callback<GetServicesModel>() {
            @Override
            public void onResponse(Call<GetServicesModel> call, Response<GetServicesModel> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    GetServicesModel finallylist = response.body();

                    String status   = finallylist.getStatus ();
                    String message   = finallylist.getStatus ();
                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<GetServicesDataModel>) finallylist.getResult();

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        setAdapter(modelList);

                    } else {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<GetServicesModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //  uploadImageBtn.setEnabled(true);
                //  cameraIcon.setEnabled(true);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
