package com.jcbriones.gmu.lab7services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PARTTWO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart1:
                startService(new Intent(MainActivity.this,
                        MusicService.class));
                Thread t = new Thread(new PartTwoThread());
                t.start();
                break;
            case R.id.buttonStart2:
                EditText text = (EditText) findViewById(R.id.time);
                int i = Integer.parseInt(text.getText().toString());
                Intent intent = new Intent(this,MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i*1000), pendingIntent);
                Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // Handler for controlling UI views
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PARTTWO:
                    // start with random wait
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(500);
                    Toast.makeText(getBaseContext(), "Jc Briones",
                            Toast.LENGTH_SHORT).show();
            }
        }};


    class PartTwoThread implements Runnable{

        // run() gets called when the thread is started
        public void run() {
            handler.sendMessage(Message.obtain(handler,
                    MainActivity.PARTTWO));
        }
    }


}


