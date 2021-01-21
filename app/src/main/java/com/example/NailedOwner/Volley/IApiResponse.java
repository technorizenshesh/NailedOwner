package com.example.NailedOwner.Volley;

import com.android.volley.VolleyError;

public interface IApiResponse {

    //void onResultReceived(JSONObject response, String tag_json_obj);
    void onResultReceived(String response, String tag_json_obj);

    void onErrorResponse(VolleyError error);

}
