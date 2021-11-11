package com.example.basicalarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static PendingIntent pendingIntent;
    public static Intent intent;
    public static Intent returnIntent(Context context){
        if(intent != null){
            return intent;
        }else {
           intent = new Intent(context, AlarmReceiver.class);
            return intent;
        }
    }
    public static PendingIntent returnPendingIntent(Context context){
        if(pendingIntent != null){
            return pendingIntent;
        }else {
            pendingIntent = PendingIntent.getBroadcast(context, 0, returnIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);
            return pendingIntent;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSettimer = (Button)findViewById(R.id.btnSetTimer);
        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        TextView txtHienThi = (TextView)findViewById(R.id.textView);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        //intent = new Intent(MainActivity.this, AlarmReceiver.class);




        btnSettimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                String string_hour = String.valueOf(hour) ;
                String string_minute = String.valueOf(minute);
                if( hour>12 ){
                    string_hour = String.valueOf(hour - 12);
                }
                if(minute <10){
                    string_minute = "0" + String.valueOf(minute);
                }
                returnIntent(MainActivity.this).putExtra("status", "on");
                //pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), returnPendingIntent(MainActivity.this));

                txtHienThi.setText("Alarm set for: " + string_hour + ":" + string_minute);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienThi.setText("Stop");
                if(returnPendingIntent(MainActivity.this) != null)
                {
                    Log.e("anh","sound off");
                    alarmManager.cancel(returnPendingIntent(MainActivity.this));
                    returnIntent(MainActivity.this).putExtra("status","off");
                    sendBroadcast(returnIntent(MainActivity.this));

                }
            }
        });
    }
}