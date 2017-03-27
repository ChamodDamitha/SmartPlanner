package com.example.chamod.smartplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Models.Date;
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
        Date alert_date=new Date(26,2,2017);



        Time task_time=new Time(14,37);
        Time alert_time=new Time(11,30);
        TimeSet timeSet=new TimeSet(alert_date,alert_time,task_time);


        TimeTask timeTask=new TimeTask("Test Full Task",date,timeSet);

        taskDB.addTimeTask(timeTask);


//        check added task

        TimeTask timeTask_back=taskDB.getTimeTask(timeTask.getId());

//        Toast.makeText(this,timeTask_back.getTimeSet().getTask_time().time_string,Toast.LENGTH_LONG).show();

        Toast.makeText(this,Integer.valueOf("05")+"",Toast.LENGTH_LONG).show();

    }
}
