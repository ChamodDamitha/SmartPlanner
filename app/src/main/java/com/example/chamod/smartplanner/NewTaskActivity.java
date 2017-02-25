package com.example.chamod.smartplanner;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chamod.smartplanner.Fragments.DateFragment;
import com.example.chamod.smartplanner.Fragments.TimeFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


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
        viewNotification(this,"dgkjjgk","dfg");

//        setAlarm();
//        Toast.makeText(this,txtDesc.getText(),Toast.LENGTH_LONG).show();
    }
    private void viewNotification(Context context,String desc,String info) {

        Intent resultIntent = new Intent(context, NotificationActivity.class);

        resultIntent.putExtra("id",122);

// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );



        Intent callIntent=new Intent();
        callIntent.putExtra("id",1212);
        callIntent.setAction("call_action");
        PendingIntent callPendingIntent=PendingIntent.getBroadcast(getApplicationContext(),0,callIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notify_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notify_icon))
                        .setContentTitle("SMART PLANNER")
                        .setContentText(desc)
                        .setContentInfo(info)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .addAction(0, "Call",callPendingIntent)
                        .addAction(0,"More", resultPendingIntent)
                ;




        mBuilder.setContentIntent(resultPendingIntent);

// Sets an ID for the notification
        int mNotificationId = 122;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }



    public void TimeClicked(View v){
//        timeFragemt.setVisibility(View.VISIBLE);
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

//        Intent i=new Intent(this,MapsActivity.class);
//        startActivity(i);
    }




    private void setAlarm(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_MONTH,25);
        cal.set(Calendar.HOUR_OF_DAY,8);
        cal.set(Calendar.MINUTE,8);

//        Long alertTime=new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent intent=new Intent(this,TaskReceiver.class);

        intent.putExtra("description","fh");

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this.getApplicationContext(),12,intent,PendingIntent.FLAG_UPDATE_CURRENT);


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
        textViewLocation.setText(place.getName());
    }

    @Override
    public void setTime(int hour, int min) {
        textViewTime.setText(hour+" : "+min);
    }

    @Override
    public void setDate(int year, int month, int day,String dayOfWeek) {
        String m=month+"",d=day+"";
        if(month<10){
            m="0"+month;
        }
        if(day<10){
            d="0"+day;
        }

        textViewDate.setText(dayOfWeek+", "+year+"-"+m+"-"+d);
    }
}
