package com.hs3773.weatherapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btn_getCityID, btn_getWeatherByCityID, btn_getWeatherByCityName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getCityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByCityID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByCityName = findViewById(R.id.btn_getWeatherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);

        // Button listener on click

        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Enter City Name", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID) {
                        if (cityID.equals("")) {
                            Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Returned City ID: " + cityID, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        btn_getWeatherByCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked button 2", Toast.LENGTH_SHORT).show();
            }
        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "You typed " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}