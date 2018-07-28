package com.example.javer.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<JSONObject> {
    JSONObject dataWeather;

    WeatherDataSet dataObj = new WeatherDataSet();


    public CustomAdapter(@NonNull Context context, ArrayList<JSONObject> days) {
        super(context, R.layout.custom_row, days);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        //get a reference
        dataWeather = (JSONObject) getItem(position);

        TextView dayText = (TextView)  customView.findViewById(R.id.day);
        TextView maxTempText = (TextView)  customView.findViewById(R.id.maxTemp);
        TextView minTempText = (TextView)  customView.findViewById(R.id.minTemp);
        TextView weatherState = (TextView)  customView.findViewById(R.id.weatherState);
        ImageView weatherPic = (ImageView) customView.findViewById(R.id.weatherPic);

        try {

            dayText.setText(dataObj.getDate(dataWeather));

            maxTempText.setText(dataObj.getMaxTemp(dataWeather));
            minTempText.setText(dataObj.getMinTemp(dataWeather));
            weatherState.setText(dataObj.getState(dataWeather));

            dataObj.LoadImage(dataWeather,weatherPic);

        }catch (Exception e){
           e.printStackTrace();
        }


        return customView;
    }
}
