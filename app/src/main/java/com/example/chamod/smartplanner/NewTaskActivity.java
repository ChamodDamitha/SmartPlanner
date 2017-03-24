package com.example.chamod.smartplanner;

import android.*;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chamod.smartplanner.BroadcastReceivers.TaskReceiver;
import com.example.chamod.smartplanner.Database.MyPlaceDB;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Fragments.TimeFragment;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.LocationTask;
import com.example.chamod.smartplanner.Models.MyPlace;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeTask;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class NewTaskActivity extends FragmentActivity implements TimeFragment.TimeFragmentListener,DateFragment.DateFragmentListener{

    TaskHandler taskHandler;

    int PLACE_PICKER_REQUEST = 1;

    EditText txtDesc,txtRange;
    TimePicker timePicker;

    TextView textViewTime,textViewDate,textViewLocation;
    AutoCompleteTextView txtViewContacts;

//    Fragments
    TimeFragment timeFragemt;
    DateFragment dateFragment;

    FragmentManager fm = getSupportFragmentManager();

//    task model references
    Date date=null;
    Location location=null;
    Time time=null,alert_time=null;

    MyPlaceDB myPlaceDB;

    CheckBox checkBoxTime,checkBoxLocation,checkBoxRepeatTask;
    LinearLayout timePad,locationPAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        taskHandler=TaskHandler.getInstance(this);

        myPlaceDB=MyPlaceDB.getInstance(this);

        txtDesc=(EditText)findViewById(R.id.txtDesc);
        txtRange=(EditText)findViewById(R.id.editTextRange);
        timePicker=(TimePicker)findViewById(R.id.timePicker);

//        TextViews
        textViewTime=(TextView)findViewById(R.id.textViewTime);
        textViewDate=(TextView)findViewById(R.id.textViewDate);
        textViewLocation=(TextView)findViewById(R.id.textViewLocation);


        timePad=(LinearLayout)findViewById(R.id.time_pad);
        locationPAd=(LinearLayout)findViewById(R.id.locationPAd);
//        set check boxes
        checkBoxTime=(CheckBox)findViewById(R.id.checkBoxTime);
        checkBoxLocation=(CheckBox)findViewById(R.id.checkBoxLocation);
        checkBoxRepeatTask=(CheckBox)findViewById(R.id.checkBoxRepeatTask);


        checkBoxTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    timePad.setVisibility(View.GONE);
                }
                else
                {
                    timePad.setVisibility(View.VISIBLE);
                }
            }
        });

        checkBoxLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    locationPAd.setVisibility(View.VISIBLE);
                }
                else{
                    locationPAd.setVisibility(View.GONE);
                }
            }
        });




//        initial date set up
        java.util.Date util_date=new java.util.Date(System.currentTimeMillis());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(util_date);

        date=new Date(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.YEAR));

        textViewDate.setText(date.getDateString());


    }





    public void showMyPlaces(View v){
        final ArrayList<MyPlace> myPlaces_list=myPlaceDB.getAllMyPlaces();

        String[] myPlaces=new String[myPlaces_list.size()];

        for (int i=0;i<myPlaces.length;i++){
            myPlaces[i]=myPlaces_list.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(NewTaskActivity.this);
        builder.setTitle("Pick among My Places")
                .setItems(myPlaces, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MyPlace myPlace=myPlaces_list.get(which);
                        setLocation(new Location(myPlace.getName(),myPlace.getLatitude(),myPlace.getLongitude()));
                    }
                });
         builder.create().show();
    }

    public void cancelTask(View v){
        Intent i=new Intent(this,NavigaterActivity.class);
        startActivity(i);
    }

    public void saveTask(View v)
    {
        String task_type;
//        not location or time ticked
        if(!checkBoxTime.isChecked() && !checkBoxLocation.isChecked()){
            Toast.makeText(this,"Please select the alert based on time or location or both....!",Toast.LENGTH_LONG).show();
        }
//        only location ticked
        else {
            if (!checkBoxTime.isChecked()) {
                //            Create a location task
                task_type="LOCATION";
            }
            //        only time ticked
            else if (!checkBoxLocation.isChecked()) {
                //            create a time task
                task_type="TIME";
            }
            //        both time and location ticked
            else {
                task_type="FULL";
            }
            if(isValidEntries()) {
                boolean success=taskHandler.saveNewTask(task_type, txtDesc.getText().toString().trim(), date, location,
                        Float.valueOf(txtRange.getText().toString().trim()), time, alert_time, checkBoxRepeatTask.isChecked());
                if(success){
                    Toast.makeText(this, "ALARM SET ...", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(this, NavigaterActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this, "ALARM WAS NOT SET ...", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
//validate entries
    private boolean isValidEntries() {
        if (txtDesc.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter the task description...!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkBoxLocation.isChecked() && location == null) {
            Toast.makeText(this, "Please select a location...!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkBoxLocation.isChecked() && txtRange.getText() == null) {
            Toast.makeText(this, "Please enter a range...!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkBoxTime.isChecked() && time == null) {
            Toast.makeText(this, "Please select a time...!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkBoxTime.isChecked() && alert_time == null) {
            Toast.makeText(this, "Please select the alert time...!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    Dialog Fragment showing
    public void TimeClicked(View v){
         timeFragemt = new TimeFragment();
        // Show DialogFragment
        timeFragemt.show(fm,"TimeFragment");
    }

    public void dateClicked(View v){
        dateFragment=new DateFragment();
        dateFragment.show(fm,"DateFragment");
    }

    public void locationClicked(View v){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        }
        catch (Exception e){
        }
    }





//    google places activity result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("MyPlace: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                setLocation(new Location(place.getName().toString(),place.getLatLng().latitude,place.getLatLng().longitude));
            }
        }
    }


    //Fragment listeners

    private  void setLocation(Location location){
        this.location=location;
        textViewLocation.setText(location.getName());
    }

    @Override
    public void setTime(int hour, int min) {
        time=new Time(hour,min);
//        need to replace logically
        alert_time=new Time(hour,min);

        textViewTime.setText(time.getTimeString());
    }

    @Override
    public void setDate(int year, int month, int day) {
        date=new Date(day,month,year);

        textViewDate.setText(date.getDateString());
    }


}
