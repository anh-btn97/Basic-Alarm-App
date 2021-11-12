package com.example.basicalarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences lastAlarm = getApplicationContext().getSharedPreferences("pname", 0);
        SharedPreferences.Editor editor = lastAlarm.edit();


        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resHour = lastAlarm.getInt("hour", -1);
                int resMinute = lastAlarm.getInt("minute", -1);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if(pendingIntent != null)
                {
                    //Log.e("anh","sound22 off");
                    //alarmManager.cancel(returnPendingIntent(NotificationActivity.this));
                    intent.putExtra("status","off");
                    sendBroadcast(intent);
                }
                Log.i("anh", "before 1p" + calendar.getTimeInMillis());
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 600000);
                Log.i("anh", "after 1p" + calendar.getTimeInMillis());
                intent.putExtra("status", "on");
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                if(resHour != -1 && resMinute != -1){
                    resMinute += 10;
                    if(resMinute >= 60){
                        resMinute -= 60;
                        resHour++;
                        if(resHour >= 24){
                            resHour -=24;
                        }
                    }
                    editor.putInt("hour", resHour);
                    editor.putInt("minute", resMinute);
                    editor.apply();
                    //txtHienThi.setText("Alarm set for: " + String.valueOf(resHour) + ":" + String.valueOf(resMinute));
                }


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
                    editor.remove("hour");
                    editor.remove("minute");
                    editor.apply();
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