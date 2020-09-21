package com.example.frodog;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class dogWeather extends AppCompatActivity {
    String rs = "";



    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogweather);


        //LocationManager location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsTracker = new GpsTracker(dogWeather.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        Geocoder gCoder = new Geocoder(this, Locale.getDefault());
        List<Address> addr = null;

        try {
            addr = gCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address a = addr.get(0);
        String add =  a.getAdminArea()+" "+a.getSubLocality()+" "+a.getThoroughfare();
        // add에 현재 위치 저장




        TextView gps = findViewById(R.id.gps);
        gps.setText(add);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("외출");
    }



}