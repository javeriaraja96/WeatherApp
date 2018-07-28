package com.example.javer.weatherapp;

import android.content.Intent;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    WeatherDataSet dataObj;

    RequestQueue queue;
    String url = "https://www.metaweather.com/api/location/";
    String woeid;

    TextView weatherState;
    TextView tempDay;
    TextView tempNight;
    TextView today;

    ConstraintLayout todaysWeather;

    ImageView imageState;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        today = (TextView) findViewById(R.id.Date);
        tempDay = (TextView) findViewById(R.id.temperatureDay);
        tempNight = (TextView) findViewById(R.id.temperatureNight);
        weatherState = (TextView) findViewById(R.id.weatherState);

        imageState = (ImageView) findViewById(R.id.imageState);
        todaysWeather = (ConstraintLayout) findViewById(R.id.todaysWeather);
        dataObj = new WeatherDataSet();




        queue = Volley.newRequestQueue(this);

        woeid = getIntent().getStringExtra("woeid");
        final String newUrl = url + woeid + "/";

        JsonObjectRequest request = new JsonObjectRequest(newUrl,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    location = response.getString("title");

                    final JSONArray consolidatedWeather =  response.getJSONArray("consolidated_weather");
                    JSONObject dataWeather = consolidatedWeather.getJSONObject(0);

                //    Toast.makeText(getApplicationContext(), dataWeather.getString("applicable_date"), Toast.LENGTH_LONG).show();

                    today.setText(dataObj.getDate(dataWeather));
                    tempDay.setText("" + dataObj.getMaxTemp(dataWeather));
                    tempNight.setText(dataObj.getMinTemp(dataWeather));
                    weatherState.setText(dataObj.getState(dataWeather));
                    dataObj.LoadImage(dataWeather,imageState);


                    ArrayList<JSONObject> upcomingDaysList = new ArrayList<JSONObject>();

                    try {
                        for (int i = 1; i < consolidatedWeather.length(); i++) {
                            upcomingDaysList.add(consolidatedWeather.getJSONObject(i));
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Too bad we couldn't make a list", Toast.LENGTH_LONG).show();
                    }

                    ListAdapter myAdapter = new CustomAdapter(getApplicationContext(), upcomingDaysList);
                    ListView myListView = (ListView) findViewById(R.id.upcomingList);

                    myListView.setAdapter(myAdapter);


                    myListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    try {
                                        makeIntent(consolidatedWeather.getJSONObject(position+1));
                                    }catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "Data not sent :(", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                    );

                    todaysWeather.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                makeIntent(consolidatedWeather.getJSONObject(0));
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Data not sent :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        request.setTag("TAG");
        queue.add(request);

    }

    public void makeIntent(JSONObject obj){
        Intent i = new Intent(getApplicationContext(), WeatherDetailActivity.class);
        i.putExtra("ITEM_EXTRA", obj.toString());
        i.putExtra("location", location);
        startActivity(i);
    }

}
