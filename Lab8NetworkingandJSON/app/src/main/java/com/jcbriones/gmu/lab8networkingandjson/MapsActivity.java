package com.jcbriones.gmu.lab8networkingandjson;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String region;
    private double latitude;
    private double longitude;
    private double magnitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        region = intent.getStringExtra(MainActivity.REGION);
        latitude = intent.getDoubleExtra(MainActivity.LATITUDE, 0);
        longitude = intent.getDoubleExtra(MainActivity.LONGITUDE, 0);
        magnitude = intent.getDoubleExtra(MainActivity.MAGNITUDE, 0);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker
        LatLng marker = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(marker).title(region));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(marker);

        // Radius of the circle
        circleOptions.radius(magnitude*30000);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);

//        List<Address> geocodeMatches = null;
//
//        try {
//            geocodeMatches =  new Geocoder(this).getFromLocationName("Melbourne, Australia", 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!geocodeMatches.isEmpty())
//        {
//            double latitude = geocodeMatches.get(0).getLatitude();
//            double longitude = geocodeMatches.get(0).getLongitude();
//            LatLng melborne = new LatLng(latitude,longitude);
//            mMap.addMarker(new MarkerOptions().position(melborne).title("Melborne"));
//        }


    }

}
