package com.example.NailedOwner.SelectCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.NailedOwner.AddSaloonPhoto.AddsaloonPhotos;
import com.example.NailedOwner.AddServices.AddServicesActivity;
import com.example.NailedOwner.Preference;
import com.example.NailedOwner.Volley.ApiRequest;
import com.example.NailedOwner.Volley.Constants;
import com.example.NailedOwner.Volley.IApiResponse;
import  com.example.NailedOwner.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectCategory extends AppCompatActivity implements IApiResponse {

    private Spinner Spn_Category;
    private Button btn_next;
    ArrayList<ChoseCategoryModel> Category=new ArrayList<>();

    private Preference preference;
    private ProgressBar progressBar;
    String android_id ="";
    String  categoryId="";
    String AddSaloon_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.black1));
        }

        AddSaloon_id = Preference.get(SelectCategory.this,Preference.KEY_Add_saloon_id);
        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        preference = new Preference(this);

        progressBar=findViewById(R.id.progressBar);
        Spn_Category=findViewById(R.id.Spn_Category);
        btn_next=findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(categoryId.equalsIgnoreCase(""))
                {
                    Toast.makeText(SelectCategory.this, "Please select your category..", Toast.LENGTH_SHORT).show();

                }else
                {
                    //Preference.save(SelectCategory.this,Preference.KEY_CategoryId,categoryId);
                    category_id_add(categoryId,AddSaloon_id);

                }
            }
        });

        if (preference.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getCategory();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    private void Category(final ArrayList<ChoseCategoryModel> Category, Spinner SpinnerNew) {

        CategoryAdapter customAdapter1=new CategoryAdapter(SelectCategory.this ,Category);
        SpinnerNew.setAdapter(customAdapter1);

        SpinnerNew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                categoryId = Category.get(position).getId().toString();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

    }


    public void category_id_add(String salon_id,String category_id){
        String UserId = Preference.get(SelectCategory.this,Preference.KEY_USER_ID);
        HashMap<String, String> map = new HashMap<>();

        map.put("salon_id",salon_id);

        map.put("category_id",category_id);

        map.put("user_id",UserId);

        ApiRequest apiRequest = new ApiRequest(SelectCategory.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_categoryid_update_by_salon, Constants.USER_categoryid_update_by_salon,map, Request.Method.POST);
    }



    public void getCategory(){

        HashMap<String, String> map = new HashMap<>();

        ApiRequest apiRequest = new ApiRequest(SelectCategory.this,this);

        apiRequest.postRequest(Constants.BASE_URL + Constants.USER_get_category, Constants.USER_get_category,map, Request.Method.GET);
    }


    @Override
    public void onResultReceived(String response, String tag_json_obj) {

        if (Constants.USER_get_category.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();

                    if(status.equalsIgnoreCase("1"))
                    {

                        getHomeModel finalArray = new Gson().fromJson(response,new TypeToken<getHomeModel>(){}.getType());

                       Category = (ArrayList<ChoseCategoryModel>) finalArray.getResult();

                        Category(Category,Spn_Category);

                    }else
                    {
                        Toast.makeText(this, "UnSuccess", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else  if (Constants.USER_categoryid_update_by_salon.equalsIgnoreCase(tag_json_obj)){

            progressBar.setVisibility(View.GONE);

            if (response !=null)  {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();

                    if(status.equalsIgnoreCase("1"))
                    {
                        Intent intent=new Intent(SelectCategory.this, AddServicesActivity.class);
                        startActivity(intent);
                        finish();

                    }else
                    {
                    /*    Intent intent=new Intent(SelectCategory.this, AddServicesActivity.class);
                        startActivity(intent);
                        finish();*/

                        Toast.makeText(this, "UnSuccess", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Please Check Your Network", Toast.LENGTH_SHORT).show();
    }

}
