package com.jcbriones.gmu.lab7services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by jayzybriones on 10/27/17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        Toast.makeText(arg0, "Time is up!!!", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

    }
}
