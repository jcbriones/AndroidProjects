package com.jcbriones.gmu.lab10location;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String LOCATION = "com.jcbriones.gmu.lab10location.static.LOCATION";
    public static final String LATITUDE = "com.jcbriones.gmu.lab10location.static.LATITUDE";
    public static final String LONGITUDE = "com.jcbriones.gmu.lab10location.static.LONGITUDE";

    private String location;
    private List<Address> addresses;
    private Geocoder geocoder;

    protected EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
    }

    private void initializeVariables() {
        searchBar = findViewById(R.id.searchBar);
        geocoder = new Geocoder(this, Locale.getDefault());
    }


    public void startMapActivity(View v) throws InterruptedException {

        location = searchBar.getText().toString();


        if (searchBar.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a location first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            addresses = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            System.out.println("Service not available");
        }

        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Location cannot be found", Toast.LENGTH_SHORT).show();
            return;
        }

        Double latitude = addresses.get(0).getLatitude();
        Double longitude = addresses.get(0).getLongitude();

        // start map with implicit intent here
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(LOCATION, location);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        startActivityForResult(intent, 10);
    }

}
