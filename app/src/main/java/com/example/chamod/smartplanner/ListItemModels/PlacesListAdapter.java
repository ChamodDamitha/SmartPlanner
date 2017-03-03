package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.Models.MyPlace;
import com.example.chamod.smartplanner.R;
import com.google.android.gms.location.places.Place;

/**
 * Created by chamod on 2/11/17.
 */

public class PlacesListAdapter extends ArrayAdapter<MyPlace> {
    public PlacesListAdapter(Context context, MyPlace[] items) {
        super(context, R.layout.place_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView=layoutInflater.inflate(R.layout.place_list_item,parent,false);


        MyPlace myPlace=getItem(position);

        TextView textViewPlaceName=(TextView)customView.findViewById(R.id.textViewPlaceName);
        textViewPlaceName.setText(myPlace.getName());
        TextView textViewPlaceAddress=(TextView)customView.findViewById(R.id.textViewPlaceAddress);
        textViewPlaceAddress.setText(myPlace.getAddress());

        return customView;
    }
}
