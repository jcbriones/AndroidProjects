package com.jcbriones.gmu.lab7services;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
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


}


