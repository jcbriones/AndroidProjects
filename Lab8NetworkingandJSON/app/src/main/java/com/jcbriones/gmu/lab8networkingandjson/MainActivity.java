package com.jcbriones.gmu.lab8networkingandjson;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String REGION = "com.jcbriones.gmu.project2workit.REGION";
    public static final String LONGITUDE = "com.jcbriones.gmu.project2workit.LONGITUDE";
    public static final String LATITUDE = "com.jcbriones.gmu.project2workit.LATITUDE";
    public static final String MAGNITUDE = "com.jcbriones.gmu.project2workit.MAGNITUDE";
    ListView list;
    ArrayList<ListData> listData;
    ArrayAdapter<String> adapter;
    static final String URL = "http://cs.gmu.edu/~white/earthquakes.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ListView(this);
        listData = new ArrayList<ListData>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startMapActivity(position);
            }
        });

        setContentView(list);

        new HttpGetTask()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,URL);

    }


    private void onFinishGetRequest(String result) {
        try {

            JSONArray earthquakes = (new JSONArray(result));
            int len = earthquakes.length();
            for (int i = 0;i<len; i++) {
                JSONObject quake = earthquakes.getJSONObject(i);
                String region = quake.getString("region");
                String mag = quake.getString("magnitude");
                String occurred = quake.getString("occurred_at");
                JSONObject quakeLocation = quake.getJSONObject("location");
                String longitude = quakeLocation.getString("longitude");
                String latitude = quakeLocation.getString("latitude");

                adapter.add(region + " (" + longitude + "," + latitude + ") with magnitude = " + mag + " on " + occurred);
                ListData ld = new ListData(region, Double.valueOf(longitude), Double.valueOf(latitude), Double.valueOf(mag));
                listData.add(ld);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private class HttpGetTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuffer data = new StringBuffer();
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) new
                        URL(params[0]).openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String rawData;
                while ((rawData = br.readLine()) != null) {
                    data.append(rawData);
                }
            } catch (MalformedURLException e1) {e1.printStackTrace();
            } catch (IOException e1) {e1.printStackTrace();
            } finally {
                if (br != null)
                    try {  br.close();
                    } catch (IOException e) {e.printStackTrace();}
            }
            return data.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //          Toast.makeText(getApplicationContext(),"String returned" + result.length(),Toast.LENGTH_SHORT).show();
            onFinishGetRequest(result);
        }
    }

    private class ListData {
        public String region;
        public double longitude;
        public double latitude;
        public double magnitude;

        public ListData(String region, double longitude, double latitude, double magnitude)
        {
            this.region = region;
            this.longitude = longitude;
            this.latitude = latitude;
            this.magnitude = magnitude;
        }
    }

}

