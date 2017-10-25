package com.jcbriones.gmu.lab7services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private final String TAG = "BlockingService" ;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand (Intent intent, int flags, int startid) {
        Log.i(TAG,"onStartCommand() entered");
        try {Thread.sleep(5000); } catch (Exception e) { }
        return START_NOT_STICKY;
    }
}
