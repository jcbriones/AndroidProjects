package com.jcbriones.gmu.lab7broadcastreceiver;

/**
 * Created by jayzybriones on 10/26/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        Toast.makeText(arg0, "Time is up!!!", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

    }


}
