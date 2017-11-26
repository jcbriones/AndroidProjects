package com.jcbriones.gmu.lab9animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AppleView mAppleView;



    /**
     * Called when Activity is first created. Turns off the title bar, sets up
     * the content views, and fires up the SnakeView.
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAppleView = (AppleView) findViewById(R.id.apples);
        mAppleView.setTextView((TextView) findViewById(R.id.text));


    }


    private void startMapActivity(int position) {
        // start map with implicit intent here
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(REGION, listData.get(position).region);
        intent.putExtra(LONGITUDE, listData.get(position).longitude);
        intent.putExtra(LATITUDE, listData.get(position).latitude);
        intent.putExtra(MAGNITUDE, listData.get(position).magnitude);
        startActivityForResult(intent, 0);
    }

}
