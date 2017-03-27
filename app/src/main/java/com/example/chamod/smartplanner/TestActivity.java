package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeSet;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);







        TaskDB taskDB= TaskDB.getInstance(this);


        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236,4.59f);

        LocationTask locationTask=new LocationTask("Test Full Task",date,location);

        taskDB.addLocationTask(locationTask);

//        check added task

        LocationTask locationTask_back=taskDB.getLocationTask(locationTask.getId());


//        Toast.makeText(this,,Toast.LENGTH_LONG).show();

    }
}
