package com.example.chamod.smartplanner.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.EventHandlers.TaskEvent;
import com.example.chamod.smartplanner.Handlers.NotificationHandler;
import com.example.chamod.smartplanner.Handlers.TaskHandler;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.TimeTask;

public class TaskReceiver extends BroadcastReceiver {
    TaskDB taskDB;

    public TaskReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        taskDB=TaskDB.getInstance(context);
        Bundle extras=intent.getExtras();

        int task_id=extras.getInt("task_id");
        String task_type=extras.getString("task_type");


        if(task_type.equals("FULL")){
            FullTask fullTask=taskDB.getFullTask(task_id);
            NotificationHandler.viewNotification(context,task_id,fullTask.getDescription(),fullTask.getLocation().getName());
            taskDB.setTaskAlerted(task_id,true);
        }
        else if(task_type.equals("TIME")){
            TimeTask timeTask=taskDB.getTimeTask(task_id);
            NotificationHandler.viewNotification(context,task_id,timeTask.getDescription(),null);
            taskDB.setTaskAlerted(task_id,true);
        }

        TaskHandler.getInstance(context).cancelTaskAlarm(task_id);

        TaskEvent.getInstance().taskChangedEventOccured();
    }


}
