package com.example.gezim.birthdayapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gezim on 5/13/2018.
 */

public class Database extends SQLiteOpenHelper {
    SQLiteDatabase database;
    Cursor cursor;

    public Database(Context context) {
        super(context, "fiekdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table BirthdayList (id integer primary key AUTOINCREMENT,name varchar(30),phonenumber varchar(30),birthdate text,msgtemplate text,sendstatus integer)");
        sqLiteDatabase.execSQL("create table SMSHistory (name varchar(30),msgtemplate text,year varchar(5))");
        sqLiteDatabase.execSQL("create table sendingTime (id integer primary key,sendingHour integer,sendingMinutes integer)");
        insert(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getContents()
    {
         database=this.getWritableDatabase();
         cursor=database.rawQuery("select * from BirthdayList",null);
        return cursor;


    }
    public Cursor getContentsWithWhere(String name)
    {
        database=this.getReadableDatabase();
        cursor=database.rawQuery("select * from BirthdayList where TRIM(name)='"+name.trim()+"'",null);
        return cursor;
    }
    public Cursor getTodaysBirthday()
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Date date = calendar.getTime();
        SimpleDateFormat dataformati = new SimpleDateFormat("MM/dd");
        String data = dataformati.format(date);
        database=this.getWritableDatabase();
        cursor=database.rawQuery("select * from BirthdayList where birthdate like '"+data+"%'",null);
        return cursor;
    }
    public Cursor getDistinct()
    {
        database=this.getWritableDatabase();
            cursor = database.rawQuery("select  distinct name from SMSHistory", null);

        return cursor;
    }
    public Cursor getMessage(String name)
    {
        database=this.getWritableDatabase();
        cursor = database.rawQuery("select  year,msgtemplate from SMSHistory where name like'"+name+"'", null);

        return cursor;

    }
    public void delete(String name)
    {
        database=this.getWritableDatabase();
        database.delete("BirthdayList","name='"+name+"'",null);

    }
    public void edit(ContentValues cv,String where)
    {
        database=this.getWritableDatabase();
        database.update("BirthdayList",cv,where,null);


    }
    public void editSendingTime(ContentValues cv,String where)
    {
        database=this.getWritableDatabase();
        database.update("sendingTime",cv,where,null);
    }
    public void insert(SQLiteDatabase database)

    {
        cursor = database.rawQuery("select  * from sendingTime", null);
        if(cursor.getCount()<=0)
        {
            database.execSQL("insert into sendingTime values(1,0,0)");
        }

    }
    public Cursor getTime()
    {

            database = this.getWritableDatabase();
            cursor=database.rawQuery("select sendingHour,sendingMinutes from sendingTime where id=1",null);
            return cursor;

        }
}
