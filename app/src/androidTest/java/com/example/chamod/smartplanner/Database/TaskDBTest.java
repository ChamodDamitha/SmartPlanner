package com.example.chamod.smartplanner.Database;

import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;

import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by chamod on 2/26/17.
 */
public class TaskDBTest {
    TaskDB taskDB;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addFullTask_getFullTask() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

        final int task_id=456;

        Date date=new Date(26,2,2017,"SUN");
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
    public void setTaskAlerted_TaskCompleted() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());
        final int task_id=123;

        Date date=new Date(26,2,2017,"SUN");
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask=new FullTask(task_id,"Test Full Task",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask);

//        check added task

        assertEquals(fullTask.isAlerted(),false);
        assertEquals(fullTask.isCompleted(),false);


        taskDB.setTaskAlerted(fullTask.getId(),true);
        taskDB.setTaskCompleted(fullTask.getId(),true);

        FullTask fullTask1=taskDB.getFullTask(task_id);


        assertEquals(fullTask1.isAlerted(),true);
        assertEquals(fullTask1.isCompleted(),true);

    }


    @Test
    public void getAllScheduledTasks() throws Exception {
        taskDB=TaskDB.getInstance(InstrumentationRegistry.getTargetContext());

//        add 2 tasks
        Date date=new Date(26,1,2017,"SUN");
        Location location=new Location("TestLocation",1.8962,45.1236);
        Time time=new Time(14,37);
        Time alert_time=new Time(11,30);

        FullTask fullTask1=new FullTask(987,"Test Full Task 1",date,location,4.5,time,alert_time);

        FullTask fullTask2=new FullTask(989,"Test Full Task 2",date,location,4.5,time,alert_time);

        taskDB.addFullTask(fullTask1);
        taskDB.addFullTask(fullTask2);


//      check method
        ArrayList<Task> tasks=taskDB.getAllScheduledTasks(date);

        assertEquals(2,tasks.size());
    }

}