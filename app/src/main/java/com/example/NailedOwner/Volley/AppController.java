package com.example.NailedOwner.Volley;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Arrays;

public class AppController extends Application {
 
    public static final String TAG = AppController.class
            .getSimpleName();
 
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    private static Context mContext;

    AppEnvironment appEnvironment;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext=this;

        appEnvironment = AppEnvironment.SANDBOX;
      /*  FacebookSdk.sdkInitialize(getApplicationContext());*/
        //Mint.initAndStartSession(this, "bb6d8d9a");
    }

    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        MultiDex.install(this);
    }*/

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

  /*  public BaseApplication(PayUmoneyActivity payUmoneyActivity) {

    }public BaseApplication() {

    }*/


    public static synchronized AppController getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
         return mRequestQueue;
    }
 
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static Context getContext(){
        return mContext;
    }


    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;


    public static <T> ArrayList<T> getGsonArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return new ArrayList<T>(Arrays.asList(arr));
    }

    public static <T> Object getGsonObject(String s, Class<T> clazz) {
        Object object = new Gson().fromJson(s, clazz);
        return object;
    }

    public static  <T> JsonArray getGsonJsonArray(ArrayList<T> clazz) {
        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(clazz).getAsJsonArray();
        return myCustomArray;
    }

}