package com.example.gezim.birthdayapp;


import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    ListView listView;
    ArrayList<User> stringlista;
    public Database databaza;
    ListViewAdapter adapteri;
    User user;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blank, container,false);
        listView=(ListView) v.findViewById(R.id.listView);
        try{perditeso(getActivity());}
        catch(Exception e){
            Log.i("Gabimi",e.getMessage());}
        return v;
    }

    @Override
    public void onResume() {    databaza=new Database(getActivity());
        perditeso(getActivity());
        super.onResume();



    }

    public void perditeso(Context c){
        databaza=new Database(c);
        Cursor data=databaza.getTodaysBirthday();
        int nr=data.getCount();
        stringlista=new ArrayList<>();
        if(nr!=0) {

            while (data.moveToNext()) {

                user = new User(data.getString(1), data.getString(3));
                stringlista.add(user);


            }
            adapteri = new ListViewAdapter(c, R.layout.birthdayrow, stringlista);
            listView.setAdapter(adapteri);

        }

    }


}
