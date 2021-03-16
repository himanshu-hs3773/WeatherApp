package com.hs3773.weatherapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";

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


    public interface ForecastByIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByID(String cityID, ForecastByIDResponse forecastByIDResponse) {
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;
        // get JSON Object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");


                            for (int i = 0; i<consolidated_weather_list.length(); i++) {

                                WeatherReportModel one_day_weather = new WeatherReportModel();
                                JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(i);
                                one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                                one_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                                one_day_weather.setMin_temp(first_day_from_api.getLong("min_temp"));
                                one_day_weather.setMax_temp(first_day_from_api.getLong("max_temp"));
                                one_day_weather.setThe_temp(first_day_from_api.getLong("the_temp"));
                                one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                                weatherReportModels.add(one_day_weather);
                            }

                            forecastByIDResponse.onResponse(weatherReportModels);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public interface GetCityForecastByName {
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByName(String cityName, GetCityForecastByName getCityForecastByName) {
        // fetch city id
        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {
                // now we have city id!
                getCityForecastByID(cityID, new ForecastByIDResponse() {
                    @Override
                    public void onError(String message) {
                    }
                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        // we have the weather forecast!
                        getCityForecastByName.onResponse(weatherReportModels);

                    }
                });

            }
        });
    }
}
