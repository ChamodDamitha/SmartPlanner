package com.example.chamod.smartplanner.Handlers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.chamod.smartplanner.BroadcastReceivers.TaskReceiver;
import com.example.chamod.smartplanner.Database.TaskDB;
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

    private static TaskHandler taskHandler=null;
    private TaskDB taskDB;

    private TaskHandler(Context context){
        taskDB=TaskDB.getInstance(context);
        this.context=context;
    }

    public static TaskHandler getInstance(Context context){
        if(taskHandler==null){
            taskHandler=new TaskHandler(context);
        }
        return taskHandler;
    }


    public boolean saveNewTask(String type, String desc, Date date, Location location, Double range,Time time,Time alert_time,boolean repeat ){
        Task task;
        if(type.equals("LOCATION")){
            LocationTask locationTask=new LocationTask(taskDB.getNextTaskId(),desc,date,location,range);
            locationTask.setRepeat(repeat);
            taskDB.addLocationTask(locationTask);
            task=locationTask;
        }
        else if(type.equals("TIME")){
            TimeTask timeTask=new TimeTask(taskDB.getNextTaskId(),desc,date,time,alert_time);
            timeTask.setRepeat(repeat);
            taskDB.addTimeTask(timeTask);
            task=timeTask;
        }
        else{
            FullTask fullTask=new FullTask(taskDB.getNextTaskId(),desc,date,location,range,time,alert_time);
            fullTask.setRepeat(repeat);
            taskDB.addFullTask(fullTask);
            task=fullTask;
        }
        setTaskAlarm(task);
        return true;
    }


    private void setTaskAlarm(Task task) {
        if (task.getType().equals("LOCATION")) {


        }
        else if(task.getType().equals("TIME")){
            TimeTask timeTask=(TimeTask)task;
            setTimeAlarm(timeTask.getId(),timeTask.getType(),timeTask.getDate(),timeTask.getAlert_time());
        }
        else {
            FullTask fullTask=(FullTask) task;
            setTimeAlarm(fullTask.getId(),fullTask.getType(),fullTask.getDate(),fullTask.getAlert_time());
        }
    }

    private void setTimeAlarm(int task_id,String task_type,Date date,Time alert_time){
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


    public Task[] getAllScheduledTaskItems(Date date){
        ArrayList<Task> tasks=taskDB.getAllScheduledTasks(date);

        Task[] scheduled_tasks=new Task[tasks.size()];
        for (int i=0;i<scheduled_tasks.length;i++){
            scheduled_tasks[i]=tasks.get(i);

        }
        return scheduled_tasks;
    }
}
