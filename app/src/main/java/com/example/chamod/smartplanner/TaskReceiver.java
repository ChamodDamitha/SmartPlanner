package com.example.chamod.smartplanner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class TaskReceiver extends BroadcastReceiver {
    public TaskReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context,intent.getStringExtra("description"));
    }

    private void showNotification(Context context,String desc) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText(desc);

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
