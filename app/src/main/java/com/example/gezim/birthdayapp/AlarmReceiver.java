package com.example.gezim.birthdayapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by Gezim on 5/25/2018.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.example.gezim.birthdayapp";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MyTestTime:","Times");
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                context.startForegroundService(new Intent(context, SMSservice.class));
            } else {
                context.startService(new Intent(context, SMSservice.class));
            }
        }
        catch(Exception e) {
            Log.i("MyTestServiceGabimi", e.getMessage());
        }
    }
}