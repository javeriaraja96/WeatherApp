package com.example.javer.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class WeatherDetailActivity extends AppCompatActivity {

    TextView dayText;

    TextView humidity;
    TextView pressure;
    TextView wind;
    TextView location;
    TextView maxTempText;
    TextView minTempText;
    TextView state;
    ImageView iconWeather;

    String loc;
    JSONObject dataWeather;

    WeatherDataSet dataObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        loc = getIntent().getStringExtra("location");

        dayText = (TextView) findViewById(R.id.dayText);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        wind = (TextView) findViewById(R.id.wind);
        location = (TextView) findViewById(R.id.location);
        maxTempText = (TextView) findViewById(R.id.maxTemp);
        minTempText = (TextView) findViewById(R.id.minTemp);
        state = (TextView) findViewById(R.id.state);
        iconWeather = (ImageView) findViewById(R.id.icon);
        dataObj = new WeatherDataSet();

        try {
            dataWeather= new JSONObject(getIntent().getStringExtra("ITEM_EXTRA"));


            dayText.setText(dataObj.getDate(dataWeather));
            humidity.setText(dataObj.getHumidity(dataWeather));
            pressure.setText(dataObj.getPressure(dataWeather));
            wind.setText(dataObj.getWindData(dataWeather));
            location.setText("Location: " + loc);
            maxTempText.setText(dataObj.getMaxTemp(dataWeather));
            minTempText.setText(dataObj.getMinTemp(dataWeather));
            state.setText(dataObj.getState(dataWeather));

            dataObj.LoadImage(dataWeather,iconWeather);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
