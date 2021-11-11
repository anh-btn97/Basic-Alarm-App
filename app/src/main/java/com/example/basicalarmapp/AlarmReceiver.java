package com.example.basicalarmapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.example.basicalarmapp.NotificationActivity.launchIntent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("ANH","123");
        String string_sound = intent.getExtras().getString("status");
        //Log.e("ANH", string_sound);

        Intent myIntent = new Intent(context, Sound.class);
        myIntent.putExtra("status", string_sound);
        context.startService(myIntent);
        if (string_sound.equals("on")){
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            nb.setContentIntent(launchAlarmLandingPage(context));
            notificationHelper.getManager().notify(1, nb.build());
        }

    }
    private static PendingIntent launchAlarmLandingPage(Context ctx) {

        return PendingIntent.getActivity(
                ctx, 0, launchIntent(ctx), FLAG_UPDATE_CURRENT
        );
    }
}
