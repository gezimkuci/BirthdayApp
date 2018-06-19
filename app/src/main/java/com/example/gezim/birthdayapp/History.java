package com.example.gezim.birthdayapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    ListView listview;
    TextView txtHistory;

    ArrayAdapter arrayAdapter;
    ArrayList<String> userLista=new ArrayList<>();
    public Database db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    listview=(ListView)findViewById(R.id.listView);
        txtHistory=(TextView) findViewById(R.id.txtHistory);




    db=new Database(getApplicationContext());
    c = db.getDistinct();

    if (c.getCount() != 0) {
        while (c.moveToNext()) {
            userLista.add( c.getString(0));
        }
        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.historyrow, R.id.txtEmri, userLista);
        listview.setAdapter(arrayAdapter);

    }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> vitet=new ArrayList<>();
                ArrayList<String> mesazhet=new ArrayList<>();
                String name=arrayAdapter.getItem(position).toString();
                c=db.getMessage(name);
                if (c.getCount() != 0) {
                    while (c.moveToNext()) {
                        vitet.add(c.getString(0));
                        mesazhet.add(c.getString(1));

                    }
                }

                    AlertDialog dialog;
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(History.this);
                    LayoutInflater inflater = History.this.getLayoutInflater();
                    View v = inflater.inflate(R.layout.message_show, null, false);
                    ListView lv=(ListView)v.findViewById(R.id.lvDialog);
                    DialogAdapter adapteri=new DialogAdapter(getApplicationContext(),R.layout.historymsgrow,vitet,mesazhet);
                    lv.setAdapter(adapteri);
                    builder.setView(v);
                    dialog = builder.create();
                    dialog.show();


            }
        });
}
}
