package com.example.gezim.birthdayapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gezim.birthdayapp.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gezim on 5/21/2018.
 */

public class ListViewAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater;
     ArrayList<User> stringLista;
    int resourceid;
    Context context;
    public ListViewAdapter(Context context, int resourceid, ArrayList<User> dataString) {
        super(context, resourceid,dataString);
        this.resourceid=resourceid;
        this.stringLista=dataString;
        this.context=context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resourceid,null);
        User user=stringLista.get(position);

        TextView txtEmri=(TextView)convertView.findViewById(R.id.txtEmri);
        TextView txtDitlindja=(TextView)convertView.findViewById(R.id.txtDitlindja);
        if(user!=null)
        {
            txtEmri.setText(user.getName());
            txtDitlindja.setText(user.getBirthday());
        }
        else{
            Toast.makeText(context,"Nuk ka te dhena!",Toast.LENGTH_LONG).show();
        }

        return convertView;
    }
}
