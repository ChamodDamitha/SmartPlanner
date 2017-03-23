package com.example.chamod.smartplanner.Database;

import android.support.test.InstrumentationRegistry;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.LocationTask;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeTask;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by chamod on 2/26/17.
 */
public class TaskDBTest {
    TaskDB taskDB;

    @Test
    public void removeTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        final int task_id=561;

        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask=new FullTask(task_id,"Test Full Task",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask);
        taskDB.removeTask(task_id);
        FullTask fullTask_back=taskDB.getFullTask(task_id);
        assertEquals(null,fullTask_back);


        LocationTask locationTask=new LocationTask(task_id+1,"Test Full Task",date,location,4.5);
        taskDB.addLocationTask(locationTask);
        LocationTask locationTask_back=taskDB.getLocationTask(task_id);
        assertEquals(null,locationTask_back);

        TimeTask timeTask=new TimeTask(task_id+2,"Test Full Task",date,time,alert_time);
        taskDB.addTimeTask(timeTask);
        TimeTask timeTask_back=taskDB.getTimeTask(task_id);
        assertEquals(null,timeTask_back);
    }



    @Test
    public void addFullTask_getFullTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        final int task_id=123;

        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask=new FullTask(task_id,"Test Full Task",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask);

//        check added task

        FullTask fullTask_back=taskDB.getFullTask(task_id);

        assertEquals(fullTask.getId(),fullTask_back.getId());
        assertEquals(fullTask.getDescription(),fullTask_back.getDescription());

        assertEquals(fullTask.getDate().getDay(),fullTask_back.getDate().getDay());
        assertEquals(fullTask.getDate().getDayOfWeek(),fullTask_back.getDate().getDayOfWeek());
        assertEquals(fullTask.getDate().getMonth(),fullTask_back.getDate().getMonth());
        assertEquals(fullTask.getDate().getMonthOfYear(),fullTask_back.getDate().getMonthOfYear());
        assertEquals(fullTask.getDate().getYear(),fullTask_back.getDate().getYear());

        assertEquals(fullTask.getLocation().getName(),fullTask_back.getLocation().getName());
        assertEquals(fullTask.getLocation().getLatitude(),fullTask_back.getLocation().getLatitude(),0.00001);
        assertEquals(fullTask.getLocation().getLongitude(),fullTask_back.getLocation().getLongitude(),0.00001);

        assertEquals(fullTask.getRange(),fullTask_back.getRange(),0.01);

        assertEquals(fullTask.getTime().get24Hour(),fullTask_back.getTime().get24Hour());
        assertEquals(fullTask.getTime().getMinute(),fullTask_back.getTime().getMinute());
        assertEquals(fullTask.getAlert_time().get24Hour(),fullTask_back.getAlert_time().get24Hour());
        assertEquals(fullTask.getAlert_time().getMinute(),fullTask_back.getAlert_time().getMinute());


    }

    @Test
    public void addLocationTask_getLocationTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        final int task_id=457;

        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236);

        LocationTask locationTask=new LocationTask(task_id,"Test Full Task",date,location,4.5);

        taskDB.addLocationTask(locationTask);

//        check added task

        LocationTask locationTask_back=taskDB.getLocationTask(task_id);

        assertEquals(locationTask.getId(),locationTask_back.getId());
        assertEquals(locationTask.getDescription(),locationTask_back.getDescription());

        assertEquals(locationTask.getDate().getDay(),locationTask_back.getDate().getDay());
        assertEquals(locationTask.getDate().getDayOfWeek(),locationTask_back.getDate().getDayOfWeek());
        assertEquals(locationTask.getDate().getMonth(),locationTask_back.getDate().getMonth());
        assertEquals(locationTask.getDate().getMonthOfYear(),locationTask_back.getDate().getMonthOfYear());
        assertEquals(locationTask.getDate().getYear(),locationTask_back.getDate().getYear());

        assertEquals(locationTask.getLocation().getName(),locationTask_back.getLocation().getName());
        assertEquals(locationTask.getLocation().getLatitude(),locationTask_back.getLocation().getLatitude(),0.00001);
        assertEquals(locationTask.getLocation().getLongitude(),locationTask_back.getLocation().getLongitude(),0.00001);

        assertEquals(locationTask.getRange(),locationTask_back.getRange(),0.01);


    }


    @Test
    public void addTimeTask_getTimeTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        final int task_id=456;

        Date date=new Date(26,2,2017);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        TimeTask timeTask=new TimeTask(task_id,"Test Full Task",date,time,alert_time);

        taskDB.addTimeTask(timeTask);

//        check added task

        TimeTask timeTask_back=taskDB.getTimeTask(task_id);

        assertEquals(timeTask.getId(),timeTask_back.getId());
        assertEquals(timeTask.getDescription(),timeTask_back.getDescription());

        assertEquals(timeTask.getDate().getDay(),timeTask_back.getDate().getDay());
        assertEquals(timeTask.getDate().getDayOfWeek(),timeTask_back.getDate().getDayOfWeek());
        assertEquals(timeTask.getDate().getMonth(),timeTask_back.getDate().getMonth());
        assertEquals(timeTask.getDate().getMonthOfYear(),timeTask_back.getDate().getMonthOfYear());
        assertEquals(timeTask.getDate().getYear(),timeTask_back.getDate().getYear());

        assertEquals(timeTask.getTime().get24Hour(),timeTask_back.getTime().get24Hour());
        assertEquals(timeTask.getTime().getMinute(),timeTask_back.getTime().getMinute());
        assertEquals(timeTask.getAlert_time().get24Hour(),timeTask_back.getAlert_time().get24Hour());
        assertEquals(timeTask.getAlert_time().getMinute(),timeTask_back.getAlert_time().getMinute());


    }


    @Test
    public void setTaskAlerted_TaskCompleted_TaskRepeated() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());
        final int task_id=123;

        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask=new FullTask(task_id,"Test Full Task",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask);

//        check added task

        assertEquals(fullTask.isAlerted(),false);
        assertEquals(fullTask.isCompleted(),false);
        assertEquals(fullTask.isRepeat(),false);



        taskDB.setTaskAlerted(fullTask.getId(),true);
        taskDB.setTaskCompleted(fullTask.getId(),true);
        taskDB.setTaskAlerted(fullTask.getId(),true);

        FullTask fullTask1=taskDB.getFullTask(task_id);


        assertEquals(fullTask1.isAlerted(),true);
        assertEquals(fullTask1.isCompleted(),true);
        assertEquals(fullTask1.isAlerted(),true);

    }




    @Test
    public void getAllScheduledTasks() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

//        add 2 tasks
        Date date=new Date(26,1,2017);
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask1=new FullTask(987,"Test Full Task 1",date,location,4.5,time,alert_time);

        FullTask fullTask2=new FullTask(989,"Test Full Task 2",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask1);
        taskDB.addFullTask(fullTask2);


//      check method
        ArrayList<Task> tasks=taskDB.getAllTasks(date);

        assertEquals(2,tasks.size());
    }

}