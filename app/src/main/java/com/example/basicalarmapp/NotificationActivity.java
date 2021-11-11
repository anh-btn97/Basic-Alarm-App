package com.example.basicalarmapp;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static com.example.basicalarmapp.MainActivity.pendingIntent;
import static com.example.basicalarmapp.MainActivity.returnIntent;
import static com.example.basicalarmapp.MainActivity.returnPendingIntent;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alert_alarm);
        final Button remindBtn = (Button) findViewById(R.id.remind_btn);
        final Button dismiss = (Button) findViewById(R.id.dismiss_btn);
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnPendingIntent(NotificationActivity.this) != null)
                {
                    //Log.e("anh","sound22 off");
                    //alarmManager.cancel(returnPendingIntent(NotificationActivity.this));
                    returnIntent(NotificationActivity.this).putExtra("status","off");
                    sendBroadcast(returnIntent(NotificationActivity.this));
                }
                startActivity(new Intent(NotificationActivity.this, MainActivity.class));
                NotificationActivity.this.finish();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnPendingIntent(NotificationActivity.this) != null)
                {
                    Log.e("anh","sound22 off");
                    alarmManager.cancel(returnPendingIntent(NotificationActivity.this));
                    returnIntent(NotificationActivity.this).putExtra("status","off");
                    sendBroadcast(returnIntent(NotificationActivity.this));
                    NotificationActivity.this.finish();

                }
            }
        });
    }
    public static Intent launchIntent(Context context) {
        final Intent i = new Intent(context, NotificationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
}