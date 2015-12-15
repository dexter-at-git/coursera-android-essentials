package com.coursera.gfil.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    private static final String TAG = "AlarmNotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mNotificationIntent = new Intent(context, SelfieListMainActivity.class);
        PendingIntent mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setAutoCancel(true).setContentTitle("Daily Selfie")
                .setContentText("Time to another selfie!")
                .setContentIntent(mContentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());

        Log.i(TAG, "Sending notification at:" + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
