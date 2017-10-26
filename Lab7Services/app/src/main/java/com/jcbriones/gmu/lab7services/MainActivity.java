package com.jcbriones.gmu.lab7services;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PARTTWO = 1;

    int awake_counter = 0;
    Button count_button ;
    MediaPlayer player;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count_button=(Button)findViewById(R.id.buttonAwake);
        player = MediaPlayer.create(this, R.raw.braincandy);
        player.setLooping(false);

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
                player.start();
                break;
            case R.id.buttonService:
                startService(new Intent(MainActivity.this,
                        MyService.class));
                break;
            case R.id.buttonIntent:
                startService(new Intent(MainActivity.this,
                        MyIntentService.class));
                break;
            case R.id.buttonBlock:
                try {Thread.sleep(5000);} catch (Exception e) { }
                break;
            case R.id.buttonAwake:
                  count_button.setText("Ui Awake? "+ (++awake_counter));
//                       stopService(new Intent(MainActivity.this, MusicService.class));
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


