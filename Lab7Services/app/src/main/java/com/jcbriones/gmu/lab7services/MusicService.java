package com.jcbriones.gmu.lab7services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    MediaPlayer player;

    private final String TAG = "MusicService" ;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() entered");
        player = MediaPlayer.create(this, R.raw.braincandy);
        player.setLooping(false);

    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy() entered");
        player.stop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startid) {
        Log.i(TAG,"onStartCommand() entered");
        player.start();
        return START_NOT_STICKY;
    }


}
