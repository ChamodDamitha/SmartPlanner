package com.example.chamod.smartplanner;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chamod.smartplanner.Fragments.TimeFragment;

public class TaskReceiver extends BroadcastReceiver {
    public TaskReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        if(intent.getIntExtra("id",0)==122){
            Toast.makeText(context,"ac "+intent.getAction()+intent.getIntExtra("id",0),Toast.LENGTH_LONG).show();

        Log.d("ffff","safasf"+intent.getIntExtra("id",0));
//        }
//
    }



    private void viewNotification(Context context,String desc,String info) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notify_icon)
                        .setContentTitle("SMART PLANNER")
                        .setContentText(desc)
                        .setContentInfo(info);

        Intent resultIntent = new Intent(context, MainActivity.class);

// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        mBuilder.setContentIntent(resultPendingIntent);

// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
