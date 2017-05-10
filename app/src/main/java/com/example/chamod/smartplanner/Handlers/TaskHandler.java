package com.example.chamod.smartplanner.Handlers;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.example.chamod.smartplanner.BroadcastReceivers.TaskReceiver;
import com.example.chamod.smartplanner.Constants;
import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.EventHandlers.TaskEvent;
import com.example.chamod.smartplanner.ListItemModels.RepeatTaskItem;
import com.example.chamod.smartplanner.Models.Date;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;
import com.example.chamod.smartplanner.Models.Tasks.Task;
import com.example.chamod.smartplanner.Models.Time;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;
import com.example.chamod.smartplanner.Models.TimeSet;

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
    public boolean saveNewTask(String type, String desc, Date date, Location location,
                               TimeSet timeSet, boolean repeat) {
        Task task;
        if (type.equals("LOCATION")) {
            LocationTask locationTask = new LocationTask(desc, date, location);
            locationTask.setRepeat(repeat);
            taskDB.addLocationTask(locationTask);
            task = locationTask;
        } else if (type.equals("TIME")) {
            TimeTask timeTask = new TimeTask(desc, date, timeSet);
            timeTask.setRepeat(repeat);
            taskDB.addTimeTask(timeTask);
            task = timeTask;
        } else {
            FullTask fullTask = new FullTask(desc, date, location, timeSet);
            fullTask.setRepeat(repeat);
            taskDB.addFullTask(fullTask);
            task = fullTask;
        }
        setTaskAlarm(task);
        return true;
    }

    public boolean saveNewTask(Task task) {
        if (task.getType().equals("LOCATION")) {
            LocationTask locationTask = (LocationTask)task;
            taskDB.addLocationTask(locationTask);
            task = locationTask;
        } else if (task.getType().equals("TIME")) {
            TimeTask timeTask = (TimeTask)task;
            taskDB.addTimeTask(timeTask);
            task = timeTask;
        } else {
            FullTask fullTask = (FullTask)task;
            taskDB.addFullTask(fullTask);
            task = fullTask;
        }
        setTaskAlarm(task);
        return true;
    }

//    ................Update a task.................................................................
public boolean updateTask(int task_id,String type, String desc, Date date, Location location,
                           TimeSet timeSet, boolean repeat) {
    Task task;
    if (type.equals("LOCATION")) {
        LocationTask templocationTask=taskDB.getLocationTask(task_id);

        LocationTask locationTask = new LocationTask(desc, date, location);
        locationTask.setId(task_id);
        locationTask.setRepeat(repeat);
        locationTask.getLocation().setLoc_id(templocationTask.getLocation().getLoc_id());

        taskDB.updateLocationTask(locationTask);
        task = locationTask;
    } else if (type.equals("TIME")) {
        TimeTask tempTimeTask=taskDB.getTimeTask(task_id);

        TimeTask timeTask = new TimeTask(desc, date, timeSet);
        timeTask.setId(task_id);
        timeTask.setRepeat(repeat);
        timeTask.getTimeSet().setTimeset_id(tempTimeTask.getTimeSet().getTimeset_id());
        taskDB.updateTimeTask(timeTask);
        task = timeTask;
    } else {
        FullTask tempFullTask=taskDB.getFullTask(task_id);

        FullTask fullTask = new FullTask(desc, date, location, timeSet);
        fullTask.setId(task_id);
        fullTask.setRepeat(repeat);
        fullTask.getLocation().setLoc_id(tempFullTask.getLocation().getLoc_id());
        fullTask.getTimeSet().setTimeset_id(tempFullTask.getTimeSet().getTimeset_id());
        taskDB.updateFullTask(fullTask);
        task = fullTask;
    }
//    cancel previous alarms
    cancelLocationAlarm(task_id);
    cancelTimeAlarm(task_id);
//    set new alarm
    setTaskAlarm(task);
    return true;
}


    private void setTaskAlarm(Task task) {
        if (task.getType().equals("LOCATION")) {
            LocationTask locationTask = (LocationTask) task;
            setLocationAlarm(locationTask.getId(), locationTask.getType(), locationTask.getLocation());

        } else if (task.getType().equals("TIME")) {
            TimeTask timeTask = (TimeTask) task;
            setTimeAlarm(timeTask.getId(), timeTask.getType(), timeTask.getTimeSet());
        } else {
            FullTask fullTask = (FullTask) task;
            setTimeAlarm(fullTask.getId(), fullTask.getType(), fullTask.getTimeSet());
            setLocationAlarm(fullTask.getId(), fullTask.getType(), fullTask.getLocation());
        }
    }

    private void setTimeAlarm(int task_id, String task_type,TimeSet timeSet) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, timeSet.getAlert_date().getYear());
        cal.set(Calendar.MONTH, timeSet.getAlert_date().getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, timeSet.getAlert_date().getDay());
        cal.set(Calendar.HOUR_OF_DAY, timeSet.getAlert_time().get24Hour());
        cal.set(Calendar.MINUTE, timeSet.getAlert_time().getMinute());


        Intent intent = new Intent(context, TaskReceiver.class);


        intent.putExtra("task_id", task_id);
        intent.putExtra("task_type", task_type);    //NEED TO REPLACE


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private void setLocationAlarm(int task_id, String task_type, Location location) {

        Intent intent = new Intent(Constants.ACTION_PROXIMITY_ALERT);

        intent.putExtra("task_id", task_id);
        intent.putExtra("task_type", task_type);

        intent.setAction(Constants.ACTION_PROXIMITY_ALERT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, 0);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

            return;
        }
        locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), location.getRange(), -1, pendingIntent);
    }


    //.............get all scheduled tasks for a date...................................................
    public Task[] getAllTasks(Date date) {
        ArrayList<Task> tasks = taskDB.getAllTasks(date);

        Task[] scheduled_tasks = new Task[tasks.size()];
        for (int i = 0; i < scheduled_tasks.length; i++) {
            scheduled_tasks[i] = tasks.get(scheduled_tasks.length - i - 1);
        }
        return scheduled_tasks;
    }

//    .............get All repeat tasks.............................................................
    public RepeatTaskItem[] getAllRepeatTasks(){
        ArrayList<Task> tasks = taskDB.getAllRepeatingTasks();

        ArrayList<RepeatTaskItem> m_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> t_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> w_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> th_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> f_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> s_tasks = new ArrayList<>();
        ArrayList<RepeatTaskItem> su_tasks = new ArrayList<>();

        for(Task task:tasks){
            if(task.getDate().getDayOfWeek().equals("MON")){
                m_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("TUE")){
                t_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("WED")){
                w_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("THU")){
                th_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("FRI")){
                f_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("SAT")){
                s_tasks.add(new RepeatTaskItem(task));
            }
            else if(task.getDate().getDayOfWeek().equals("SUN")){
                su_tasks.add(new RepeatTaskItem(task));
            }
        }

        if(m_tasks.size()>0){
            m_tasks.get(0).setFirstItem(true);
        }
        if(t_tasks.size()>0){
            t_tasks.get(0).setFirstItem(true);
        }
        if(w_tasks.size()>0){
            w_tasks.get(0).setFirstItem(true);
        }
        if(th_tasks.size()>0){
            th_tasks.get(0).setFirstItem(true);
        }
        if(f_tasks.size()>0){
            f_tasks.get(0).setFirstItem(true);
        }
        if(s_tasks.size()>0){
            s_tasks.get(0).setFirstItem(true);
        }
        if(su_tasks.size()>0){
            su_tasks.get(0).setFirstItem(true);
        }

        RepeatTaskItem[] repeatTaskItems=new RepeatTaskItem[tasks.size()];

        int i=0;

        for (RepeatTaskItem r:m_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:t_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:w_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:th_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:f_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:s_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        for (RepeatTaskItem r:su_tasks) {
            repeatTaskItems[i]=r;
            i++;
        }
        return repeatTaskItems;
    }

//    .............get a task.......................................................................
    public Task getTask(int task_id,String type){
        if(type.equals(Constants.LOCATION_TYPE)){
            return taskDB.getLocationTask(task_id);
        }
        if(type.equals(Constants.TIME_TYPE)){
            return taskDB.getTimeTask(task_id);
        }
        if(type.equals(Constants.FULL_TYPE)){
            return taskDB.getFullTask(task_id);
        }
        return null;
    }


    //...................Complete a task................................................................
    public void completeTask(int task_id, boolean complete) {
        taskDB.setTaskCompleted(task_id, complete);
    }
    //...................Repeat a task................................................................
    public void setRepeatTask(int task_id, boolean repeat) {
        taskDB.setTaskRepeat(task_id, repeat);
    }

    //..................Remove a scheduled task...................................................................
    public boolean removeTask(Task task) {
        taskDB.removeTask(task.getId());
        cancelTimeAlarm(task.getId());
        cancelLocationAlarm(task.getId());
        TaskEvent.getInstance().taskChangedEventOccured();
        return true;
    }

//    .........................Cancel Alarm..................................................
    public void cancelTaskAlarm(int task_id){
        cancelLocationAlarm(task_id);
        cancelTimeAlarm(task_id);
    }

    //................Cancel Time alarm
    private void cancelTimeAlarm(int task_id) {
        Intent intent = new Intent(context, TaskReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    //................Cancel Location Alarm.
    private void cancelLocationAlarm(int task_id) {

        Intent intent = new Intent(Constants.ACTION_PROXIMITY_ALERT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, 0);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeProximityAlert(pendingIntent);
    }
}
