package com.example.chamod.smartplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.MyPlaceDB;
import com.example.chamod.smartplanner.ListItemModels.PlacesListAdapter;
import com.example.chamod.smartplanner.Models.MyPlace;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

public class MyPlacesActivity extends AppCompatActivity {
    private MyPlaceDB myPlaceDB;
    private ListView listViewPlaces;

    private EditText editTextPlaceName;
    private TextView textViewLocAddress;

    private Place google_place=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        editTextPlaceName=(EditText)findViewById(R.id.editTextPlaceName);
        textViewLocAddress=(TextView)findViewById(R.id.textViewLocAddress);


//.......setting listview at start..................................................

        listViewPlaces=(ListView)findViewById(R.id.ListViewPlaces);
        myPlaceDB=MyPlaceDB.getInstance(this);

        setListView();
    }

    public void setListView(){
        ArrayList<MyPlace> myPlaces_list=myPlaceDB.getAllMyPlaces();

        MyPlace[] myPlaces=new MyPlace[myPlaces_list.size()];
        for (int i=0;i<myPlaces.length;i++){
            myPlaces[i]=myPlaces_list.get(i);
        }

        ArrayAdapter placesListAdapter=new PlacesListAdapter(this,myPlaces,this);
        listViewPlaces.setAdapter(placesListAdapter);
    }

    private final int PLACE_PICKER_REQUEST = 145;
    public void PlacelocationClicked(View v){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        }
        catch (Exception e){
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Place picker returned the location
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                google_place = PlacePicker.getPlace(data, this);
                textViewLocAddress.setText(google_place.getName());
            }
        }
    }

    public void saveMyPlace(View v){
        if(editTextPlaceName.getText().toString().length()==0){
            Toast.makeText(getBaseContext(),"Please enter a place name...!",Toast.LENGTH_SHORT).show();
        }
        else if(google_place==null){
            Toast.makeText(getBaseContext(),"Please select a location...!",Toast.LENGTH_SHORT).show();
        }
        else if(myPlaceDB.isPlaceNameTaken(editTextPlaceName.getText().toString())){
            Toast.makeText(getBaseContext(),"Entered place name is already taken...!",Toast.LENGTH_SHORT).show();
        }
        else{
            MyPlace myPlace=new MyPlace(editTextPlaceName.getText().toString(),google_place.getName().toString(),
                    google_place.getLatLng().latitude,google_place.getLatLng().longitude);
            myPlaceDB.addMyPlace(myPlace);
            Toast.makeText(getBaseContext(),"Place added successfully",Toast.LENGTH_SHORT).show();
            setListView();

            editTextPlaceName.setText("");
            editTextPlaceName.requestFocus();

            textViewLocAddress.setText("Select Location");
            google_place=null;
        }
    }
}
