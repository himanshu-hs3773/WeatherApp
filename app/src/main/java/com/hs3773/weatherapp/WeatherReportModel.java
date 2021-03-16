package com.hs3773.weatherapp;

public class WeatherReportModel {
    private int id;
    private String weather_state_name;
    private String wind_direction_compass;
    private float min_temp;
    private float max_temp;
    private float the_temp;
    private float wind_direction;
    private int humidity;

    public WeatherReportModel() {
    }

    @Override
    public String toString() {
        return  " Forecast : '" + weather_state_name + '\'' +'\n' +
                " Current Temp= '" + the_temp + '\'' + '\n' +
                " Humidity = '" + humidity + '\'' +'\n' +
                " Low = '" + min_temp + '\'' +'\n' +
                " High = '" + max_temp + '\'' +'\n' +
                " Wind Direction = '" + wind_direction_compass + '\''
                ;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    public void setWind_direction_compass(String wind_direction_compass) {
        this.wind_direction_compass = wind_direction_compass;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

}
