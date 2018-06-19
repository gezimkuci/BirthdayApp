package com.example.gezim.birthdayapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Gezim on 5/25/2018.
 */

public class TestService extends Service {
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    @Override
    public void onCreate() {
super.onCreate();
        alarmi();


    }
    public void alarmi()
    {

        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), SMSservice.class);
        alarmIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 01);
        calendar.set(Calendar.MINUTE,9);
        calendar.set(Calendar.SECOND,0);
        Calendar kohareale=Calendar.getInstance();
        kohareale.setTimeInMillis(System.currentTimeMillis());

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                24*60*60000,alarmIntent);


        Log.i("Koha reale test",String.valueOf(kohareale.getTimeInMillis()));
        Log.i("Koha e caktuar test",String.valueOf(calendar.getTimeInMillis()));}
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
