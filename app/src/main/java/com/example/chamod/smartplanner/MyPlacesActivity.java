package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.chamod.smartplanner.ListItemModels.PlacesListAdapter;

public class MyPlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

//        String[] places={"Campus","Boarding","Seminar room","Home"};
//
//        ListView listViewPlaces=(ListView)findViewById(R.id.ListViewPlaces);
//        ArrayAdapter placesListAdapter=new PlacesListAdapter(this,places);
//        listViewPlaces.setAdapter(placesListAdapter);

    }
}
