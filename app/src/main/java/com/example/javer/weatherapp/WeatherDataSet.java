package com.example.javer.weatherapp;

import android.text.format.DateFormat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class WeatherDataSet {

    public String getHumidity(JSONObject obj){
        try {
            return "Humidity: " + String.valueOf((int) Float.parseFloat(obj.getString("humidity"))) + " %";
        }
        catch (Exception e){
            return "";
        }

    }

    public String getPressure(JSONObject obj){

        try {
            return "Pressure: "+ String.valueOf((int) Float.parseFloat(obj.getString("air_pressure"))) + " hPa ";
        }
        catch (Exception e){
            return "";
        }

    }
    public String getWindData(JSONObject obj){

        try {
            return "Wind: "+ String.valueOf((int) Float.parseFloat(obj.getString("wind_speed"))) +"km/h " +
                    obj.getString("wind_direction_compass");
        }
        catch (Exception e){
            return "";
        }

    }
    public void LoadImage(JSONObject obj, ImageView view){

        try {
            Picasso.get().load("https://www.metaweather.com/static/img/weather/png/" + obj.getString("weather_state_abbr") +".png").into(view);
        }
        catch (Exception e){
            return;
        }

    }

    public String getMaxTemp(JSONObject obj){

        try {
            int maxTemp = (int) Float.parseFloat(obj.getString("max_temp"));
            return String.valueOf(maxTemp);
        }
        catch (Exception e){
            return "";
        }

    }

    public String getMinTemp(JSONObject obj){

        try {
            int minTemp = (int) Float.parseFloat(obj.getString("min_temp"));
            return String.valueOf(minTemp);
        }
        catch (Exception e){
            return "";
        }

    }


    public String getState(JSONObject obj){

        try {
            return obj.getString("weather_state_name");
        }
        catch (Exception e){
            return "";
        }


    }


    public String getDate(JSONObject obj){

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return ((String) DateFormat.format("EEE, MMM d, ''yy", simpleDateFormat.parse(obj.getString("applicable_date"))));
        }
        catch (Exception e){
            return "";
        }

    }




}
