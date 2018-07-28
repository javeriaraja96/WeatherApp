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
//    TextView dateText;
    TextView humidity;
    TextView pressure;
    TextView wind;
    TextView location;
    TextView maxTempText;
    TextView minTempText;
    TextView state;
    ImageView iconWeather;

    JSONObject dataWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        try {
            dataWeather= new JSONObject(getIntent().getStringExtra("ITEM_EXTRA"));

            dayText = (TextView) findViewById(R.id.dayText);
//            dateText = (TextView) findViewById(R.id.dateText);
            humidity = (TextView) findViewById(R.id.humidity);
            pressure = (TextView) findViewById(R.id.pressure);
            wind = (TextView) findViewById(R.id.wind);
            location = (TextView) findViewById(R.id.location);
            maxTempText = (TextView) findViewById(R.id.maxTemp);
            minTempText = (TextView) findViewById(R.id.minTemp);
            state = (TextView) findViewById(R.id.state);
            iconWeather = (ImageView) findViewById(R.id.icon);

            Picasso.get().load("https://www.metaweather.com/static/img/weather/png/" + dataWeather.getString("weather_state_abbr") +".png").into(iconWeather);

            int minTemp = (int) Float.parseFloat(dataWeather.getString("min_temp"));
            String minTempString = String.valueOf(minTemp);

            int maxTemp = (int) Float.parseFloat(dataWeather.getString("max_temp"));
            String maxTempString = String.valueOf(maxTemp);

            maxTempText.setText(maxTempString);
            minTempText.setText(minTempString);

            location.setText("Location: " + getIntent().getStringExtra("location"));
            state.setText(dataWeather.getString("weather_state_name"));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = (String) DateFormat.format("EEE, MMM d, ''yy", simpleDateFormat.parse(dataWeather.getString("applicable_date")));
            dayText.setText(date);

//            String dateFind = (String) DateFormat.format("", simpleDateFormat.parse(dataWeather.getString("applicable_date"),new ParsePosition(0)))
//            dateText.setText(dateFind);
            humidity.setText("Humidity: "+ String.valueOf((int) Float.parseFloat(dataWeather.getString("humidity")))+ " %");
            pressure.setText("Pressure: "+ String.valueOf((int) Float.parseFloat(dataWeather.getString("air_pressure"))) + " hPa ");
            wind.setText("Wind: "+ String.valueOf((int) Float.parseFloat(dataWeather.getString("wind_speed"))) +"km/h " +
                    dataWeather.getString("wind_direction_compass"));




        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
