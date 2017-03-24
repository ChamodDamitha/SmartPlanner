package com.example.chamod.smartplanner.Handlers;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;

import com.example.chamod.smartplanner.BroadcastReceivers.TaskReceiver;
import com.example.chamod.smartplanner.Calculaters.TimeCompare;
import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.EventHandlers.TaskEvent;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.LocationTask;
import com.example.chamod.smartplanner.Models.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.TimeTask;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chamod on 3/19/17.
 */

public class TaskHandler {
    private Context context;

    private static TaskHandler taskHandler = null;
    private TaskDB taskDB;

    private TaskHandler(Context context) {
        taskDB = TaskDB.getInstance(context);
        this.context = context;
    }

    public static TaskHandler getInstance(Context context) {
        if (taskHandler == null) {
            taskHandler = new TaskHandler(context);
        }
        return taskHandler;
    }

    //..............Save a new task.....................................................................
    public boolean saveNewTask(String type, String desc, Date date, Location location, float range,
                               Time time, Time alert_time, boolean repeat) {
        Task task;
        if (type.equals("LOCATION")) {
            LocationTask locationTask = new LocationTask(taskDB.getNextTaskId(), desc, date, location, range);
            locationTask.setRepeat(repeat);
            taskDB.addLocationTask(locationTask);
            task = locationTask;
        } else if (type.equals("TIME")) {
            TimeTask timeTask = new TimeTask(taskDB.getNextTaskId(), desc, date, time, alert_time);
            timeTask.setRepeat(repeat);
            taskDB.addTimeTask(timeTask);
            task = timeTask;
        } else {
            FullTask fullTask = new FullTask(taskDB.getNextTaskId(), desc, date, location, range, time, alert_time);
            fullTask.setRepeat(repeat);
            taskDB.addFullTask(fullTask);
            task = fullTask;
        }
        setTaskAlarm(task);
        return true;
    }

    private void setTaskAlarm(Task task) {
        if (task.getType().equals("LOCATION")) {
            LocationTask locationTask=(LocationTask)task;
            setLocationAlarm(locationTask.getId(),locationTask.getType(),locationTask.getLocation(),locationTask.getRange());

        } else if (task.getType().equals("TIME")) {
            TimeTask timeTask = (TimeTask) task;
            setTimeAlarm(timeTask.getId(), timeTask.getType(), timeTask.getDate(), timeTask.getAlert_time());
        } else {
            FullTask fullTask = (FullTask) task;
            setTimeAlarm(fullTask.getId(), fullTask.getType(), fullTask.getDate(), fullTask.getAlert_time());
            setLocationAlarm(fullTask.getId(),fullTask.getType(),fullTask.getLocation(),fullTask.getRange());
        }
    }

    private void setTimeAlarm(int task_id, String task_type, Date date, Time alert_time) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, date.getYear());
        cal.set(Calendar.MONTH, date.getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, date.getDay());
        cal.set(Calendar.HOUR_OF_DAY, alert_time.get24Hour());
        cal.set(Calendar.MINUTE, alert_time.getMinute());


        Intent intent = new Intent(context, TaskReceiver.class);


        intent.putExtra("task_id", task_id);
        intent.putExtra("task_type", task_type);    //NEED TO REPLACE


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private void setLocationAlarm(int task_id, String task_type, Location location, float range) {

        Intent intent = new Intent(Constants.ACTION_PROXIMITY_ALERT);

        intent.putExtra("task_id", task_id);
        intent.putExtra("task_type", task_type);


        PendingIntent pendingIntent = PendingIntent.getService(context, task_id, intent, 0);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity)context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

            return;
        }
        locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), range, -1, pendingIntent);
    }




//.............get all scheduled tasks for a date...................................................
    public Task[] getAllTasks(Date date){
        ArrayList<Task> tasks=taskDB.getAllTasks(date);

        Task[] scheduled_tasks=new Task[tasks.size()];
        for (int i=0;i<scheduled_tasks.length;i++){
            scheduled_tasks[i]=tasks.get(scheduled_tasks.length-i-1);
        }
        return scheduled_tasks;
    }
//...................Complete a task................................................................
    public void completeTask(int task_id,boolean complete){
        taskDB.setTaskCompleted(task_id,complete);
    }

//..................Remove a scheduled task...................................................................
    public boolean removeTask(Task task){
        taskDB.removeTask(task.getId());
        cancelTimeAlarm(task.getId());
        TaskEvent.getInstance().taskChangedEventOccured();
        return true;
    }

//................Cancel Time alarm
    private void cancelTimeAlarm(int task_id){
        Intent intent=new Intent(context,TaskReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,task_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
