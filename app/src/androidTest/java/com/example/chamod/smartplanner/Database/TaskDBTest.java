package com.example.chamod.smartplanner.Database;

import android.support.test.InstrumentationRegistry;
import android.util.Log;
import android.widget.Toast;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Message;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.MessageTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.TimeSet;

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


        Date date=new Date(26,2,2017);
        Date alert_date=new Date(2,12,2018);
        Location location=new Location("TestLocation",1.8962,45.1236,1.5f);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);
        TimeSet timeSet=new TimeSet(alert_date,alert_time,time);

        FullTask fullTask=new FullTask("Test Full Task",date,location,timeSet);

        taskDB.addFullTask(fullTask);
        taskDB.removeTask(fullTask.getId());
        FullTask fullTask_back=taskDB.getFullTask(fullTask.getId());
        assertEquals(null,fullTask_back);


        LocationTask locationTask=new LocationTask("Test Full Task",date,location);
        taskDB.addLocationTask(locationTask);
        taskDB.removeTask(locationTask.getId());
        LocationTask locationTask_back=taskDB.getLocationTask(locationTask.getId());
        assertEquals(null,locationTask_back);

        TimeTask timeTask=new TimeTask("Test Full Task",date,timeSet);
        taskDB.addTimeTask(timeTask);
        taskDB.removeTask(timeTask.getId());
        TimeTask timeTask_back=taskDB.getTimeTask(timeTask.getId());
        assertEquals(null,timeTask_back);

        Message message=new Message("content","0715468698");
        MessageTask messageTask=new MessageTask("blaaa",date,message);
        taskDB.addMessageTask(messageTask);
        taskDB.removeTask(messageTask.getId());
        MessageTask messageTask_back=taskDB.getMessageTask(messageTask.getId());
        assertEquals(null,messageTask_back);
    }


    @Test
    public void addMessageTask_getMessageTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        Date date=new Date(26,2,2017);
        Message message=new Message("content","0715468698");
        MessageTask messageTask=new MessageTask("blaaa",date,message);
        taskDB.addMessageTask(messageTask);
        MessageTask messageTask_back=taskDB.getMessageTask(messageTask.getId());

        assertEquals(messageTask_back.getId(),messageTask.getId());
        assertEquals(messageTask_back.getDescription(),messageTask.getDescription());

        assertEquals(messageTask_back.getMessage().getMsg_id(),messageTask.getMessage().getMsg_id());
        assertEquals(messageTask_back.getMessage().getContent(),messageTask.getMessage().getContent());
        assertEquals(messageTask_back.getMessage().getReceiver_no(),messageTask.getMessage().getReceiver_no());

        assertEquals(messageTask_back.getDate().getDay(),messageTask.getDate().getDay());
        assertEquals(messageTask_back.getDate().getDayOfWeek(),messageTask.getDate().getDayOfWeek());
        assertEquals(messageTask_back.getDate().getMonth(),messageTask.getDate().getMonth());
        assertEquals(messageTask_back.getDate().getMonthOfYear(),messageTask.getDate().getMonthOfYear());
        assertEquals(messageTask_back.getDate().getYear(),messageTask.getDate().getYear());


    }


    @Test
    public void addFullTask_getFullTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());


        Date date=new Date(26,2,2017);
        Date alert_date=new Date(2,1,2018);
        Location location=new Location("TestLocation",1.8962,45.1236,2.312f);
        Time task_time=new Time(14,37);
        Time alert_time=new Time(11,30);
        TimeSet timeSet=new TimeSet(alert_date,alert_time,task_time);

        FullTask fullTask=new FullTask("Test Full Task",date,location,timeSet);

        taskDB.addFullTask(fullTask);

//        check added task

        FullTask fullTask_back=taskDB.getFullTask(fullTask.getId());

        assertEquals(fullTask.getId(),fullTask_back.getId());
        assertEquals(fullTask.getDescription(),fullTask_back.getDescription());

        assertEquals(fullTask.getDate().getDay(),fullTask_back.getDate().getDay());
        assertEquals(fullTask.getDate().getDayOfWeek(),fullTask_back.getDate().getDayOfWeek());
        assertEquals(fullTask.getDate().getMonth(),fullTask_back.getDate().getMonth());
        assertEquals(fullTask.getDate().getMonthOfYear(),fullTask_back.getDate().getMonthOfYear());
        assertEquals(fullTask.getDate().getYear(),fullTask_back.getDate().getYear());

        assertEquals(fullTask.getTimeSet().getAlert_date().getDay(),fullTask_back.getTimeSet().getAlert_date().getDay());
        assertEquals(fullTask.getTimeSet().getAlert_date().getDayOfWeek(),fullTask_back.getTimeSet().getAlert_date().getDayOfWeek());
        assertEquals(fullTask.getTimeSet().getAlert_date().getMonth(),fullTask_back.getTimeSet().getAlert_date().getMonth());
        assertEquals(fullTask.getTimeSet().getAlert_date().getMonthOfYear(),fullTask_back.getTimeSet().getAlert_date().getMonthOfYear());
        assertEquals(fullTask.getTimeSet().getAlert_date().getYear(),fullTask_back.getTimeSet().getAlert_date().getYear());

        assertEquals(fullTask.getLocation().getName(),fullTask_back.getLocation().getName());
        assertEquals(fullTask.getLocation().getLatitude(),fullTask_back.getLocation().getLatitude(),0.00001);
        assertEquals(fullTask.getLocation().getLongitude(),fullTask_back.getLocation().getLongitude(),0.00001);
        assertEquals(fullTask.getLocation().getRange(),fullTask_back.getLocation().getRange(),0.01);

        assertEquals(fullTask.getTimeSet().getTask_time().get24Hour(),fullTask_back.getTimeSet().getTask_time().get24Hour());
        assertEquals(fullTask.getTimeSet().getTask_time().getMinute(),fullTask_back.getTimeSet().getTask_time().getMinute());
        assertEquals(fullTask.getTimeSet().getAlert_time().get24Hour(),fullTask_back.getTimeSet().getAlert_time().get24Hour());
        assertEquals(fullTask.getTimeSet().getAlert_time().getMinute(),fullTask_back.getTimeSet().getAlert_time().getMinute());


    }

    @Test
    public void addLocationTask_getLocationTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());


        Date date=new Date(26,2,2017);
        Location location=new Location("TestLocation",1.8962,45.1236,4.59f);

        LocationTask locationTask=new LocationTask("Test Full Task",date,location);

        taskDB.addLocationTask(locationTask);

//        check added task

        LocationTask locationTask_back=taskDB.getLocationTask(locationTask.getId());

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

        assertEquals(locationTask.getLocation().getRange(),locationTask_back.getLocation().getRange(),0.01);


    }


    @Test
    public void addTimeTask_getTimeTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        Date date=new Date(26,2,2017);
        Date alert_date=new Date(26,2,2017);
        Time task_time=new Time(14,37);
        Time alert_time=new Time(11,30);
        TimeSet timeSet=new TimeSet(alert_date,alert_time,task_time);


        TimeTask timeTask=new TimeTask("Test Full Task",date,timeSet);

        taskDB.addTimeTask(timeTask);


//        check added task

        TimeTask timeTask_back=taskDB.getTimeTask(timeTask.getId());


        assertEquals(timeTask.getId(),timeTask_back.getId());
        assertEquals(timeTask.getDescription(),timeTask_back.getDescription());



        assertEquals(timeTask.getDate().getDay(),timeTask_back.getDate().getDay());
        assertEquals(timeTask.getDate().getDayOfWeek(),timeTask_back.getDate().getDayOfWeek());
        assertEquals(timeTask.getDate().getMonth(),timeTask_back.getDate().getMonth());
        assertEquals(timeTask.getDate().getMonthOfYear(),timeTask_back.getDate().getMonthOfYear());
        assertEquals(timeTask.getDate().getYear(),timeTask_back.getDate().getYear());

        assertEquals(timeTask.getTimeSet().getAlert_date().getDay(),timeTask_back.getTimeSet().getAlert_date().getDay());
        assertEquals(timeTask.getTimeSet().getAlert_date().getDayOfWeek(),timeTask_back.getTimeSet().getAlert_date().getDayOfWeek());
        assertEquals(timeTask.getTimeSet().getAlert_date().getMonth(),timeTask_back.getTimeSet().getAlert_date().getMonth());
        assertEquals(timeTask.getTimeSet().getAlert_date().getMonthOfYear(),timeTask_back.getTimeSet().getAlert_date().getMonthOfYear());
        assertEquals(timeTask.getTimeSet().getAlert_date().getYear(),timeTask_back.getTimeSet().getAlert_date().getYear());

        assertEquals(timeTask.getTimeSet().getTask_time().get24Hour(),timeTask_back.getTimeSet().getTask_time().get24Hour());
        assertEquals(timeTask.getTimeSet().getTask_time().getMinute(),timeTask_back.getTimeSet().getTask_time().getMinute());
        assertEquals(timeTask.getTimeSet().getAlert_time().get24Hour(),timeTask_back.getTimeSet().getAlert_time().get24Hour());
        assertEquals(timeTask.getTimeSet().getAlert_time().getMinute(),timeTask_back.getTimeSet().getAlert_time().getMinute());


    }


//    @Test
//    public void setTaskAlerted_TaskCompleted_TaskRepeated() throws Exception {
//        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());
//        final int task_id=123;
//
//        Date date=new Date(26,2,2017);
//        Location location=new Location("TestLocation",1.8962,45.1236);
//        Time time=new Time(14,37);
//        Time alert_time=new Time(11,30);
//
//        FullTask fullTask=new FullTask(task_id,"Test Full Task",date,location,4.5f,time,alert_time);
//
//        taskDB.addFullTask(fullTask);
//
////        check added task
//
//        assertEquals(fullTask.isAlerted(),false);
//        assertEquals(fullTask.isCompleted(),false);
//        assertEquals(fullTask.isRepeat(),false);
//
//
//
//        taskDB.setTaskAlerted(fullTask.getId(),true);
//        taskDB.setTaskCompleted(fullTask.getId(),true);
//        taskDB.setTaskAlerted(fullTask.getId(),true);
//
//        FullTask fullTask1=taskDB.getFullTask(task_id);
//
//
//        assertEquals(fullTask1.isAlerted(),true);
//        assertEquals(fullTask1.isCompleted(),true);
//        assertEquals(fullTask1.isAlerted(),true);
//
//
//        LocationTask locationTask=new LocationTask(task_id+1,"Test Full Task",date,location,4.5f);
//
//        taskDB.addLocationTask(locationTask);
//
////        check added task
//
//        assertEquals(locationTask.isAlerted(),false);
//        assertEquals(locationTask.isCompleted(),false);
//        assertEquals(locationTask.isRepeat(),false);
//
//
//
//        taskDB.setTaskAlerted(locationTask.getId(),true);
//        taskDB.setTaskCompleted(locationTask.getId(),true);
//        taskDB.setTaskAlerted(locationTask.getId(),true);
//
//        LocationTask locationTask1=taskDB.getLocationTask(task_id+1);
//
//
//        assertEquals(locationTask1.isAlerted(),true);
//        assertEquals(locationTask1.isCompleted(),true);
//        assertEquals(locationTask1.isAlerted(),true);
//
//    }
//
//
//
//
//    @Test
//    public void getAllScheduledTasks() throws Exception {
//        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());
//
////        add 2 tasks
//        Date date=new Date(26,1,2017);
//        Location location=new Location("TestLocation",1.8962,45.1236);
//        Time time=new Time(14,37);
//        Time alert_time=new Time(11,30);
//
//        FullTask fullTask1=new FullTask(987,"Test Full Task 1",date,location,4.5f,time,alert_time);
//
//        FullTask fullTask2=new FullTask(989,"Test Full Task 2",date,location,4.5f,time,alert_time);
//
//        taskDB.addFullTask(fullTask1);
//        taskDB.addFullTask(fullTask2);
//
//
////      check method
//        ArrayList<Task> tasks=taskDB.getAllTasks(date);
//
//        assertEquals(2,tasks.size());
//    }

}