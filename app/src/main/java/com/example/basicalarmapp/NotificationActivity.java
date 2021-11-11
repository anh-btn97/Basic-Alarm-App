package com.example.basicalarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class NotificationActivity extends AppCompatActivity {
    public PendingIntent pendingIntent;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alert_alarm);
        final Button remindBtn = (Button) findViewById(R.id.remind_btn);
        final Button dismiss = (Button) findViewById(R.id.dismiss_btn);
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();

        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if(pendingIntent != null)
                {
                    //Log.e("anh","sound22 off");
                    //alarmManager.cancel(returnPendingIntent(NotificationActivity.this));
                    intent.putExtra("status","off");
                    sendBroadcast(intent);
                }
                Log.i("anh", "before 1p" + calendar.getTimeInMillis());
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 120000);
                Log.i("anh", "after 1p" + calendar.getTimeInMillis());
                intent.putExtra("status", "on");
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                NotificationActivity.this.finish();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if(pendingIntent != null)
                {
                    Log.e("anh","sound22 off");
                    alarmManager.cancel(pendingIntent);
                    intent.putExtra("status","off");
                    sendBroadcast(intent);
                    NotificationActivity.this.finish();
                }
            }
        });
    }
    public static Intent launchIntent(Context context) {
        final Intent i = new Intent(context, NotificationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return i;
    }
}