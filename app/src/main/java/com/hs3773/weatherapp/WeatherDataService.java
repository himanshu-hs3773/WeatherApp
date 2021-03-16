package com.hs3773.weatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String cityID);
    }
    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        cityID = "";
                        try {
                            JSONObject cityInfo = response.getJSONObject(0);
                            cityID = cityInfo.getString("woeid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(cityID);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("Something wrong");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
}

//    public List<WeatherReportModel> getCityForecastByID(String cityID) {
//        return null;
//    }
//
//    public List<WeatherReportModel> getCityForecastByName(String cityName) {
//        return null;
//    }
}
