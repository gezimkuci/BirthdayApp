package com.example.gezim.birthdayapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gezim on 6/8/2018.
 */

public class DialogAdapter extends ArrayAdapter<String> {
ArrayList <String> vitet;
    ArrayList <String> msgtemplate;
    Context context;
    int resource;
    LayoutInflater inflater;

    public DialogAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> vitet, @NonNull ArrayList<String> msgtemplate) {
        super(context, resource, vitet);
    this.vitet=vitet;
    this.msgtemplate=msgtemplate;
    this.context=context;
    this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resource,null);
        TextView txtYear=(TextView)convertView.findViewById(R.id.txtYear);
        TextView txtMsgTemplate=(TextView)convertView.findViewById(R.id.txtMsgTemplate);
        txtMsgTemplate.setText(msgtemplate.get(position));
        txtYear.setText(vitet.get(position));



        return convertView;
    }
}
