package com.example.NailedOwner.Utills;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClients {

    private static final String BASE_URL ="http://nailedbeauty.me/nailed/webservice/";
    private static RetrofitClients mInstance;
    private Retrofit retrofit;


    private RetrofitClients(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

public static synchronized RetrofitClients getInstance(){

        if (mInstance == null){
       mInstance = new RetrofitClients();
        }
        return mInstance;
    }

  public Api getApi(){

   return retrofit.create(Api.class);

  }

}
