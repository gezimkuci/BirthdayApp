package com.example.gezim.birthdayapp;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gezim.birthdayapp.Database;
import com.example.gezim.birthdayapp.User;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class Blank3Fragment extends Fragment {
    FloatingActionButton floatingActionButton;
   ListView listView;
    ArrayList <User> stringlista;
    public Database databaza;
    ListViewAdapter adapteri;
    User user;
    static final int PICK_CONTACT = 1;
    public Blank3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               // Inflate the layout for this fragment
      View v=inflater.inflate(R.layout.fragment_blank3, container,false);
        listView=(ListView) v.findViewById(R.id.listView);
    floatingActionButton=(FloatingActionButton)v.findViewById(R.id.floatingActionButton4);
   floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Register.class);
                startActivity(intent);
            }
        });
        perditeso(getActivity());

        return v;


    }

    @Override
    public void onResume() {
        databaza=new Database(getActivity());
     perditeso(getActivity());
        super.onResume();



    }

public void perditeso(Context c){
    databaza=new Database(c);
    Cursor data=databaza.getContents();
    int nr=data.getCount();
    stringlista=new ArrayList<>();
        if(nr!=0) {

            while (data.moveToNext()) {

                user = new User(data.getString(1), data.getString(3));
                user.setMsgtemplate(data.getString(4));
                stringlista.add(user);


            }



        }
    adapteri = new ListViewAdapter(c, R.layout.birthdayrow, stringlista);
    listView.setAdapter(adapteri);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AlertDialog dialog;
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_edit, null, false);
            ImageButton btnDelete=(ImageButton)v.findViewById(R.id.btnDelete);
            ImageButton btnEdit=(ImageButton)v.findViewById(R.id.btnEdit);
            final TextView txtEmri=(TextView)v.findViewById(R.id.txtName);
            TextView txtBirthdate=(TextView)v.findViewById(R.id.txtBirthDate);
            TextView txtMessage=(TextView)v.findViewById(R.id.txtMessage);
            User useri=adapteri.getItem(position);
            txtEmri.setText(useri.getName());
            txtBirthdate.setText(useri.getBirthday());
            txtMessage.setText(useri.getMsgtemplate());

            builder.setView(v);
            dialog = builder.create();
            dialog.show();
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        databaza.delete(txtEmri.getText().toString());
                        perditeso(getActivity());
                        dialog.cancel();
                    }
                    catch(Exception e)
                    {
                        Log.i("MyTest",e.getMessage());
                    }
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),Edit.class);
                    intent.putExtra("NAME",txtEmri.getText());
                    startActivity(intent);
                    dialog.cancel();
                }
            });


        }
    });

    }
}
