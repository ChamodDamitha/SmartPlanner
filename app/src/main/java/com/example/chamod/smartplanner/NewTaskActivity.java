package com.example.chamod.smartplanner;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chamod.smartplanner.BroadcastReceivers.TaskReceiver;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Fragments.TimeFragment;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

public class NewTaskActivity extends FragmentActivity implements TimeFragment.TimeFragmentListener,DateFragment.DateFragmentListener{

    int PLACE_PICKER_REQUEST = 1;

    EditText txtDesc;
    TimePicker timePicker;

    TextView textViewTime,textViewDate,textViewLocation;
    AutoCompleteTextView txtViewContacts;

//    Fragments
    TimeFragment timeFragemt;
    DateFragment dateFragment;

    FragmentManager fm = getSupportFragmentManager();

//    task model references
    Date date;
    Location location;
    Time time,alert_time;

    TaskDB taskDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        taskDB=TaskDB.getInstance(this);

        date=null;
        time=null;
        alert_time=null;
        location=null;


        txtDesc=(EditText)findViewById(R.id.txtDesc);
        timePicker=(TimePicker)findViewById(R.id.timePicker);

//        TextViews
        textViewTime=(TextView)findViewById(R.id.textViewTime);
        textViewDate=(TextView)findViewById(R.id.textViewDate);
        textViewLocation=(TextView)findViewById(R.id.textViewLocation);

//        Contacts autocomplete
        txtViewContacts=(AutoCompleteTextView)findViewById(R.id.txtViewContacts);

        String[] contacts=new String[]{"Belgium", "France", "Italy", "Germany", "Spain"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,contacts);

        txtViewContacts.setAdapter(adapter);
        txtViewContacts.setThreshold(1);

    }

    public void saveTask(View v)
    {
//        need to replace
        if(txtDesc.getText().toString()!="" && date!=null && location!=null && time!=null && alert_time!=null) {

            FullTask fullTask = new FullTask(taskDB.getNextTaskId(), txtDesc.getText().toString(), date, location,
                    0, time, alert_time);

            taskDB.addFullTask(fullTask);

            setAlarm(fullTask.getId(), fullTask.getType());

            Toast.makeText(this, "ALARM SET ...", Toast.LENGTH_LONG).show();
        }
    }


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




    private void setAlarm(int task_id,String task_type){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,date.getYear());
        cal.set(Calendar.MONTH,date.getMonth()-1);
        cal.set(Calendar.DAY_OF_MONTH,date.getDay());
        cal.set(Calendar.HOUR_OF_DAY,alert_time.get24Hour());
        cal.set(Calendar.MINUTE,alert_time.getMinute());


        Intent intent=new Intent(this,TaskReceiver.class);


        intent.putExtra("task_id",task_id);
        intent.putExtra("task_type",task_type);    //NEED TO REPLACE


        PendingIntent pendingIntent=PendingIntent.getBroadcast(this.getApplicationContext(),task_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }


//    google places activity result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                setLocation(place);
            }
        }
    }


    //Fragment listeners

    private  void setLocation(Place place){
        location=new Location(place.getName().toString(),place.getLatLng().latitude,place.getLatLng().longitude);
        textViewLocation.setText(place.getName());
    }

    @Override
    public void setTime(int hour, int min) {
        time=new Time(hour,min);
//        need to replace logically
        alert_time=new Time(hour,min);

        textViewTime.setText(hour+" : "+min);
    }

    @Override
    public void setDate(int year, int month, int day,String dayOfWeek) {
        date=new Date(day,month,year,dayOfWeek);

        textViewDate.setText(dayOfWeek+", "+day+" "+date.getMonthOfYear()+" "+year);
    }
}
