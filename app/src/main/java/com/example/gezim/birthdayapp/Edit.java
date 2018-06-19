package com.example.gezim.birthdayapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Edit extends AppCompatActivity {
    TextView txtDescription;
    private Calendar calendar=Calendar.getInstance();
    private EditText etBirthdate;
    private EditText etNumber;
    private EditText etName;
    private Button btnSave;
    private Button btnCancel;
    private EditText etsms;
    private Spinner spinner;
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etBirthdate= (EditText) findViewById(R.id.etBirthdate);
        etName = (EditText) findViewById(R.id.etName);
        etNumber= (EditText) findViewById(R.id.etNumber);
        etsms=(EditText)findViewById(R.id.etsms);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        spinner=(Spinner)findViewById(R.id.spinner3);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etBirthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             try {
                 new DatePickerDialog(Edit.this, date, calendar
                         .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                         calendar.get(Calendar.DAY_OF_MONTH)).show();
             }
             catch(Exception e)
             {Log.i("MyTestGabimi",e.getMessage());
             }

            }
        });

        txtDescription = (TextView) findViewById(R.id.txtDescription);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "font/dancingscript_regular.ttf");
        txtDescription.setTypeface(customFont);

             final String name = getIntent().getStringExtra("NAME");
             db=new Database(Edit.this);
          try {

              Cursor c = db.getContentsWithWhere(name);
                if(c.getCount()>0) {
                    c.moveToFirst();
                    etName.setText(c.getString(1));
                    etNumber.setText(c.getString(2));
                    etBirthdate.setText(c.getString(3));
                    etsms.setText(c.getString(4));
                }
          }
          catch(Exception e)
          {
              Log.i("MyTestEdit",""+e.getMessage());

          }

             btnCancel.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     finish();
                 }
             });
             btnSave.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     ContentValues cv = new ContentValues();
                     cv.put("name", etName.getText().toString());
                     cv.put("phonenumber", etNumber.getText().toString());
                     cv.put("birthdate", etBirthdate.getText().toString());
                     cv.put("msgtemplate", etsms.getText().toString());

                     if (spinner.getSelectedItemPosition() == 0)
                         cv.put("sendstatus", 0);
                     else
                         cv.put("sendstatus", 1);
                     db.edit(cv, "name='" + name + "'");

                     Toast.makeText(Edit.this,
                             "Te dhenat u ruajten me sukses!",
                             Toast.LENGTH_LONG).show();
                     finish();


                 }
             });



    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etBirthdate.setText(sdf.format(calendar.getTime()));
    }
    }


