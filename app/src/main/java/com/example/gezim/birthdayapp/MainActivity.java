package com.example.gezim.birthdayapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import junit.framework.Test;

import java.lang.annotation.Target;
import java.nio.channels.AlreadyBoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       try {
           scheduleAlarm();
       }
       catch(Exception e)
       {
           Log.i("MyTest",e.getMessage());
       }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabsPager tabsPager=new tabsPager(getSupportFragmentManager());
        viewPager.setAdapter(tabsPager);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    
    public void scheduleAlarm() {
        int hour=0;
        int minute=0;
        int second=0;
        Database db=new Database(MainActivity.this);
        Cursor cursor=db.getTime();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            hour=cursor.getInt(0);
            minute=cursor.getInt(1);
        }
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar now = Calendar.getInstance();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        long timetotrigger;
        if (calendar.getTimeInMillis() <= now.getTimeInMillis())
            timetotrigger=calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            timetotrigger=calendar.getTimeInMillis();


        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, timetotrigger,
                AlarmManager.INTERVAL_DAY, pIntent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

             Intent intent = new Intent(MainActivity.this, History.class);
             startActivity(intent);

         }
        else if (id == R.id.nav_time) {
           try {
                final AlertDialog dialogshoworcancel;

               final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
               LayoutInflater inflater = MainActivity.this.getLayoutInflater();
               View v = (View) inflater.inflate(R.layout.timepicker_dialog, null, false);
               Button btnCancel=(Button)v.findViewById(R.id.btnCancel);
               Button btnSave=(Button)v.findViewById(R.id.btnSave);
               final TimePicker timePicker=(TimePicker)v.findViewById(R.id.timePicker);
               dialog.setView(v);
               dialogshoworcancel=dialog.create();
               dialogshoworcancel.show();
               btnSave.setOnClickListener(new View.OnClickListener() {
                   @TargetApi(23)
                   @Override
                   public void onClick(View view) {
                       timePicker.is24HourView();
                       int hour=timePicker.getHour();
                       int minutes=timePicker.getMinute();
                       ContentValues cv=new ContentValues();
                       cv.put("sendingHour",hour);
                       cv.put("sendingMinutes",minutes);
                       Database db=new Database(MainActivity.this);
                       db.editSendingTime(cv,"id=1");
                       scheduleAlarm();
                       dialogshoworcancel.cancel();
                   }
               });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogshoworcancel.cancel();
                }
            });
           }
           catch(Exception e)
           {Log.i("MyTest",e.getMessage());}

        } else if (id == R.id.nav_about) {



                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = (View) inflater.inflate(R.layout.about_dialog, null, false);
                dialog.setView(v);
                dialog.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }}

