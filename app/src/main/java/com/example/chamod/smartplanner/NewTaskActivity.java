package com.example.chamod.smartplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.MyPlaceDB;
import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Fragments.ReminderFragment;
import com.example.chamod.smartplanner.Fragments.TimeFragment;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.MyPlace;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class NewTaskActivity extends FragmentActivity implements TimeFragment.TimeFragmentListener,
        DateFragment.DateFragmentListener,ReminderFragment.ReminderFragmentListener {

    TaskHandler taskHandler;

    int PLACE_PICKER_REQUEST = 1;

    EditText txtDesc,txtRange;
    TimePicker timePicker;

    TextView textViewTime,textViewDate,textViewLocation;
    AutoCompleteTextView txtViewContacts;

//    Fragments
    TimeFragment timeFragemt;
    DateFragment dateFragment;
    ReminderFragment reminderFragment;

    FragmentManager fm = getSupportFragmentManager();


//    task model references
    int task_id=-1;

    Date date=null;
    Location location=null;
    Time time=null;
    long rem_before=0;
    TimeSet timeSet=null;

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
        locationPAd=(LinearLayout)findViewById(R.id.locationPad);
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

//        set received intent variables
        task_id=getIntent().getIntExtra("task_id",-1);
        if(task_id!=-1){
            setInitialData(task_id,getIntent().getStringExtra("task_type"));
        }

    }


    private void setInitialData(int task_id,String type){
        Task task=taskHandler.getTask(task_id, type);

        txtDesc.setText(task.getDescription());

        if(task.getType().equals(Constants.FULL_TYPE)){
            FullTask fullTask=(FullTask)task;

            location=fullTask.getLocation();
            textViewLocation.setText(location.getName());
            txtRange.setText(location.getRange()+"");

            time=fullTask.getTimeSet().getTask_time();
            textViewTime.setText(time.getTimeString());

            checkBoxTime.setChecked(true);
            checkBoxLocation.setChecked(true);
        }
        else if(task.getType().equals(Constants.LOCATION_TYPE)){
            LocationTask locationTask=(LocationTask) task;
            location=locationTask.getLocation();
            textViewLocation.setText(location.getName());
            txtRange.setText(location.getRange()+"");

            checkBoxTime.setChecked(false);
            checkBoxLocation.setChecked(true);
            timePad.setVisibility(View.GONE);
        }
        else if(task.getType().equals(Constants.TIME_TYPE)){
            TimeTask timeTask=(TimeTask) task;
            time=timeTask.getTimeSet().getTask_time();
            textViewTime.setText(time.getTimeString());

            checkBoxTime.setChecked(true);
            checkBoxLocation.setChecked(false);
            locationPAd.setVisibility(View.GONE);
        }
        date=task.getDate();
        textViewDate.setText(date.getDateString());

        checkBoxRepeatTask.setChecked(task.isRepeat());
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
                        setLocation(myPlace.getName(), myPlace.getLatitude(), myPlace.getLongitude());
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
                task_type=Constants.LOCATION_TYPE;
            }
            //        only time ticked
            else if (!checkBoxLocation.isChecked()) {
                //            create a time task
                task_type=Constants.TIME_TYPE;
            }
            //        both time and location ticked
            else {
                task_type=Constants.FULL_TYPE;
            }
            if(isValidEntries()) {
                boolean success;
                if(task_id==-1) {
                    success = taskHandler.saveNewTask(task_type, txtDesc.getText().toString().trim(), date, location,
                            timeSet, checkBoxRepeatTask.isChecked());
                }
                else{
                    success=taskHandler.updateTask(task_id,task_type, txtDesc.getText().toString().trim(), date, location,
                            timeSet, checkBoxRepeatTask.isChecked());
                }
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
        }
        else{
            try{
                float range=Float.valueOf(txtRange.getText().toString());
                if(range<1){
                    Toast.makeText(this,"The minimum range is 1 m ...!",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            catch (Exception e){
                Toast.makeText(this,"Please enter a valid range...!",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

//    Dialog Fragment showing
    public void timeClicked(View v){
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

    public void viewReminderClicked(View v){
        reminderFragment=new ReminderFragment();
        // Show DialogFragment
        reminderFragment.show(fm,"ReminderFragment");
    }





//    google places activity result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                setLocation(place.getName().toString(), place.getLatLng().latitude,
                            place.getLatLng().longitude);

            }
        }
    }


    //Fragment listeners

    private  void setLocation(String name,double latitude,double longitude){
        float range;

        try{
            range=Float.valueOf(txtRange.getText().toString().trim());
            if(range<1){
                range=1;
                txtRange.setText(range+"");
            }
        }
        catch (NumberFormatException e){
            range=1;
            txtRange.setText(range+"");
        }

        this.location=new Location(name,latitude,longitude,range);
        textViewLocation.setText(location.getName());
    }

    @Override
    public void setTime(int hour, int min) {
        time=new Time(hour,min);
        setRem();
        textViewTime.setText(time.getTimeString());
    }

    @Override
    public void setDate(int year, int month, int day) {
        date=new Date(day,month,year);
        setRem();
        textViewDate.setText(date.getDateString());
    }


    @Override
    public void setReminderBefore(long miliseconds) {
        rem_before=miliseconds;
        setRem();
    }

//    calculate alert date and time
    private void setRem(){
        if(date!=null && time!=null){
            Date alert_date=date;
            Time alert_time=time;

//        need to replace
            timeSet=new TimeSet(alert_date,alert_time,time);
        }
    }
}
