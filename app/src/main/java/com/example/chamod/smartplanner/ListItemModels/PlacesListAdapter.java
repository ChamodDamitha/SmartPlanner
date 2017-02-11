package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 2/11/17.
 */

public class PlacesListAdapter extends ArrayAdapter<String> {
    public PlacesListAdapter(Context context, String[] items) {
        super(context, R.layout.place_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView=layoutInflater.inflate(R.layout.place_list_item,parent,false);


        TextView textViewPlace=(TextView)customView.findViewById(R.id.textViewPlace);
        textViewPlace.setText(getItem(position));

        return customView;
    }
}
