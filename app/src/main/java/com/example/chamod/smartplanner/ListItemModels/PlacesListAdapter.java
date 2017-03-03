package com.example.chamod.smartplanner.ListItemModels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.MyPlaceDB;
import com.example.chamod.smartplanner.Models.MyPlace;
import com.example.chamod.smartplanner.MyPlacesActivity;
import com.example.chamod.smartplanner.R;
import com.google.android.gms.location.places.Place;

/**
 * Created by chamod on 2/11/17.
 */

public class PlacesListAdapter extends ArrayAdapter<MyPlace> {
    MyPlaceDB myPlaceDB;
    Context context;

    MyPlacesActivity myPlacesActivity;

    public PlacesListAdapter(Context context, MyPlace[] items,MyPlacesActivity myPlacesActivity) {
        super(context, R.layout.place_list_item, items);

        myPlaceDB = MyPlaceDB.getInstance(context);
        this.context=context;
        this.myPlacesActivity=myPlacesActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View customView = layoutInflater.inflate(R.layout.place_list_item, parent, false);


        final MyPlace myPlace = getItem(position);

        TextView textViewPlaceName = (TextView) customView.findViewById(R.id.textViewPlaceName);
        textViewPlaceName.setText(myPlace.getName());
        TextView textViewPlaceAddress = (TextView) customView.findViewById(R.id.textViewPlaceAddress);
        textViewPlaceAddress.setText(myPlace.getAddress());


        FloatingActionButton btnEdit = (FloatingActionButton) customView.findViewById(R.id.btnEditPlace);
        FloatingActionButton btnDel = (FloatingActionButton) customView.findViewById(R.id.btnDelPlace);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtNewPlaceName=new EditText(context);

                new AlertDialog.Builder(context)
                        .setView(txtNewPlaceName)
                        .setTitle("Change the place name")
                        .setMessage("Enter new place name for '"+myPlace.getName()+"'")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with change
                                boolean changed=myPlaceDB.changePlaceName(myPlace.getPlace_id(),txtNewPlaceName.getText().toString());
                                if(changed)
                                {
                                    Toast.makeText(context,"Place name was changed...",Toast.LENGTH_SHORT).show();
                                    myPlacesActivity.setListView();
                                }
                                else{
                                    Toast.makeText(context,"Place name is already taken...!",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Remove "+myPlace.getName())
                        .setMessage("Are you sure you want to remove the place ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                myPlaceDB.removeMyPlace(myPlace.getPlace_id());
                                Toast.makeText(context,"Place was removed...",Toast.LENGTH_SHORT).show();
                                myPlacesActivity.setListView();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return customView;
    }



}