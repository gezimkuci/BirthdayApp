package com.example.gezim.birthdayapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Calendar;

public class SMSservice extends IntentService {
    public SMSservice() {
        super("SMSservice");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("MyTestService", "Service running");

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        sendSMS();
    }
    private void sendSMS() {
        String number = "";
        String msg = "";
        String name="";
        int status;

        Database db=new Database(SMSservice.this);
        Cursor c=db.getTodaysBirthday();
        ContentValues contentValues=new ContentValues();
        SQLiteDatabase sqLiteDatabase=(new Database(SMSservice.this)).getWritableDatabase();

        while(c.moveToNext())
        {
            number=c.getString(2);
            msg=c.getString(4);
            name=c.getString(1);
            status=c.getInt(5);
            try {
                if (!msg.isEmpty() && !number.isEmpty()&&status==0) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, msg, null, null);
                    contentValues.put("msgtemplate", msg);
                    contentValues.put("name", name);
                    contentValues.put("year", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

                    sqLiteDatabase.insert("SMSHistory", null, contentValues);
                }
                else if(status==1)
                {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    createNotificationChannel();
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"channel")
                            .setSmallIcon(R.drawable.birthdateicon)
                            .setContentTitle("Birthday notification")
                            .setContentText("Today is "+name+"'s birthday!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(123456, mBuilder.build());
                }
            }
            catch(Exception e)
                {
                    Log.d("Gabimi:", e.getMessage());
                }


    }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="channelname";
            String description = "channeldescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}