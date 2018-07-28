package com.example.javer.weatherapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class CityChooseActivity extends AppCompatActivity {

    TextInputLayout cityName;
    TextView errorText;
    RequestQueue queue;
    String url = "https://www.metaweather.com/api/location/search/?query=";
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);

        cityName = (TextInputLayout) findViewById(R.id.CityNameLayout);
        errorText = (TextView) findViewById(R.id.errorText);

        queue = Volley.newRequestQueue(this);

    }

    public void submit(View view){

        if(!(cityName.getEditText().getText().toString().trim().equals(""))){
            city = cityName.getEditText().getText().toString().toLowerCase().replace(" ", "");
            String newUrl = url + city;


            queue.cancelAll("TAG");
            JsonArrayRequest request = new JsonArrayRequest(newUrl, new Response.Listener<JSONArray>(){

                @Override
                public void onResponse(JSONArray response) {
                    try {

                        String woeid = response.getJSONObject(0).getString("woeid");
                        Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                        i.putExtra("woeid", woeid);
                        startActivity(i);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        errorText.setText("City not found. Re-enter city name.");
                    }

                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {

                    errorText.setText("City not found. Re-enter city name.");

                }
            });

            request.setTag("TAG");
            queue.add(request);
        }
        else{
            errorText.setText("Enter city name.");
        }
    }

}
