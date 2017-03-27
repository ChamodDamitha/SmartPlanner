package com.example.chamod.smartplanner.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import com.example.chamod.smartplanner.Database.TaskDB;
import com.example.chamod.smartplanner.Handlers.NotificationHandler;
import com.example.chamod.smartplanner.Models.Tasks.FullTask;
import com.example.chamod.smartplanner.Models.Tasks.LocationTask;

public class LocationReceiver extends BroadcastReceiver {
    TaskDB taskDB;
    public LocationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Loc Receiver works",Toast.LENGTH_SHORT).show();


        taskDB=TaskDB.getInstance(context);

        final boolean entering=intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,false);

        int task_id=intent.getIntExtra("task_id",-1);
        String task_type=intent.getStringExtra("task_type");

        if(task_type.equals("LOCATION")){
            LocationTask locationTask=taskDB.getLocationTask(task_id);
            NotificationHandler.viewNotification(context,task_id,locationTask.getDescription(),"Arrived at "+locationTask.getLocation().getName());
        }
        else if(task_type.equals("FULL")){
            FullTask fullTask=taskDB.getFullTask(task_id);
            NotificationHandler.viewNotification(context,task_id,fullTask.getDescription() + "/n"+"on "+fullTask.getTimeSet().getTask_time().getTimeString(),
                    "Arrived at "+fullTask.getLocation().getName());
        }
        taskDB.setTaskAlerted(task_id,true);

//................Check entering or leaving
        if (entering){

        }
        else{

        }
    }


}
