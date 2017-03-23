package com.example.chamod.smartplanner.BroadcastReceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.chamod.smartplanner.EventHandlers.TaskEvent;
import com.example.chamod.smartplanner.Handlers.TaskHandler;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        TaskHandler taskHandler=TaskHandler.getInstance(context);
        int task_id=intent.getIntExtra("task_id",-1);

        if(intent.getAction().toString().equals("complete_action")){
            taskHandler.completeTask(task_id,true);
        }
        else{
            taskHandler.completeTask(task_id,false);
        }

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(task_id);

        TaskEvent.getInstance().taskChangedEventOccured();
    }
}
