package com.example.basicalarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public PendingIntent pendingIntent;
    public Intent intent;
    /*public static Intent returnIntent(Context context){
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
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSettimer = (Button)findViewById(R.id.btnSetTimer);
        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        TextView txtHienThi = (TextView)findViewById(R.id.textView);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        SharedPreferences lastAlarm = getApplicationContext().getSharedPreferences("pname", 0);
        SharedPreferences.Editor editor = lastAlarm.edit();
        int resHour = lastAlarm.getInt("hour", -1);
        int resMinute = lastAlarm.getInt("minute", -1);
        if(resHour != -1 && resMinute != -1){
            txtHienThi.setText("Alarm set for " + String.valueOf(resHour) + ":" +((resMinute < 10) ? ("0" + String.valueOf(resMinute)) : String.valueOf(resMinute)));
        }
        Log.e("anh.btn", "lasttime " + resHour + ":" + resMinute );


        btnSettimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentMilis = calendar.getTimeInMillis();
                Log.i("anh", "before set time" + calendar.getTimeInMillis());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                if(calendar.getTimeInMillis() <= currentMilis ){
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + 86400000);
                }
                Log.i("anh", "after set time" + calendar.getTimeInMillis());
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                editor.putInt("hour", hour);
                editor.putInt("minute", minute);
                editor.apply();
                String string_hour = String.valueOf(hour) ;
                String string_minute = String.valueOf(minute);
                /*if( hour>12 ){
                    string_hour = String.valueOf(hour - 12);
                }*/
                if(minute <10){
                    string_minute = "0" + String.valueOf(minute);
                }
                intent.putExtra("status", "on");
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.i("anh", "start alarm" + calendar.getTimeInMillis());

                txtHienThi.setText("Alarm set for " + string_hour + ":" + string_minute);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienThi.setText("Stop");
                if(pendingIntent == null)
                {
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                Log.e("anh","sound off");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("status","off");
                sendBroadcast(intent);
                editor.remove("hour");
                editor.remove("minute");
                editor.apply();
            }
        });
    }
}