package com.example.basicalarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    PendingIntent pendingIntent;


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

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);


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
                intent.putExtra("status", "on");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                txtHienThi.setText("Alarm set for: " + string_hour + ":" + string_minute);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienThi.setText("Stop");
                if(pendingIntent != null)
                {
                    Log.e("anh","sound off");
                    alarmManager.cancel(pendingIntent);
                    intent.putExtra("status","off");
                    sendBroadcast(intent);
                }
            }
        });
    }
}