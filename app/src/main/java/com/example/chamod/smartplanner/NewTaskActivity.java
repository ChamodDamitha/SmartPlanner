package com.example.chamod.smartplanner;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    EditText txtDesc;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


        txtDesc=(EditText)findViewById(R.id.txtDesc);
        timePicker=(TimePicker)findViewById(R.id.timePicker);
    }

    public void saveTask(View v)
    {
        setAlarm();
        Toast.makeText(this,txtDesc.getText(),Toast.LENGTH_LONG).show();
    }

    private void setAlarm(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_MONTH,16);
        cal.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        cal.set(Calendar.MINUTE,timePicker.getMinute());

//        Long alertTime=new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent intent=new Intent(this,TaskReceiver.class);

        intent.putExtra("description",txtDesc.getText().toString());

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this.getApplicationContext(),12,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }


}
