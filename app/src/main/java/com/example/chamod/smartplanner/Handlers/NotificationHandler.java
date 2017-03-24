package com.example.chamod.smartplanner.Handlers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.chamod.smartplanner.NotificationActivity;
import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 3/24/17.
 */

public class NotificationHandler {

    public static void viewNotification(Context context, int task_id, String desc, String info) {
        Intent resultIntent = new Intent(context, NotificationActivity.class);


// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        task_id,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );



        Intent completeIntent=new Intent();
        completeIntent.putExtra("task_id",task_id);
        completeIntent.setAction("complete_action");
        PendingIntent completePendingIntent=PendingIntent.getBroadcast(context.getApplicationContext(),0,completeIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent forgetIntent=new Intent();
        forgetIntent.putExtra("task_id",task_id);
        forgetIntent.setAction("forget_action");
        PendingIntent forgetPendingIntent=PendingIntent.getBroadcast(context.getApplicationContext(),0,forgetIntent,PendingIntent.FLAG_UPDATE_CURRENT);




        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notify_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.notify_icon))
                        .setContentTitle("SMART PLANNER")
                        .setContentText(desc)
                        .setContentInfo(info)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .addAction(0, "COMPLETE",completePendingIntent)
                        .addAction(0,"FORGET", forgetPendingIntent)
                ;




        mBuilder.setContentIntent(resultPendingIntent);

// Sets an ID for the notification
        int mNotificationId = task_id;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
