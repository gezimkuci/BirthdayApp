package com.example.gezim.birthdayapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {
    Button btnManualisht, btnKontaktet;
    TextView txtDescription;
    private final int PICK_CONTACT = 1;
    private Calendar calendar;
    private EditText etBirthdate;
    private EditText etNumber;
    private EditText etName;
    private  AlertDialog.Builder objBuilder;
    private AlertDialog dialog;
    private Button btnSave;
    private Button btnCancel;
    private EditText etsms;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        calendar = Calendar.getInstance();

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
                // TODO Auto-generated method stub
                new DatePickerDialog(Register.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtDescription = (TextView) findViewById(R.id.txtDescription);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "font/dancingscript_regular.ttf");
        txtDescription.setTypeface(customFont);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=(new Database(Register.this)).getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", etName.getText().toString());
                cv.put("phonenumber", etNumber.getText().toString());
                cv.put("birthdate", etBirthdate.getText().toString());
                cv.put("msgtemplate",etsms.getText().toString());
                if(spinner.getSelectedItemPosition()==0)
                cv.put("sendstatus",0);
                else
                    cv.put("sendstatus",1);
                long rezultati = db.insert("BirthdayList",null,cv);
                if(rezultati>0) {
                    Toast.makeText(Register.this,
                            "Te dhenat u ruajten me sukses!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            else
                {
                    Toast.makeText(Register.this,
                            "Gabim!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        objBuilder = new AlertDialog.Builder(Register.this);
        LayoutInflater inflater = Register.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_chooser, null, false);
        btnManualisht = (Button) v.findViewById(R.id.btnManualisht);
        btnKontaktet = (Button) v.findViewById(R.id.btnKontaktet);
        objBuilder.setView(v);
        btnKontaktet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSingleContact();
            }
        }
        );
        dialog = objBuilder.create();
        dialog.show();
        btnManualisht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etBirthdate.setText(sdf.format(calendar.getTime()));
    }


    private void selectSingleContact() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent,PICK_CONTACT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                       String number=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        etName.setText(name);
                        etNumber.setText(number);
                    }
                }
                break;
        }
    dialog.cancel();
    }
}
