package com.example.basicalarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ANH","123");
        String string_sound = intent.getExtras().getString("status");
        Log.e("ANH", string_sound);

        Intent myIntent = new Intent(context, Sound.class);
        myIntent.putExtra("status", string_sound);
        context.startService(myIntent);
    }
}
