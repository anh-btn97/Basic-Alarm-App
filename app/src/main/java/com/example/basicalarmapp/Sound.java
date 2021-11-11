package com.example.basicalarmapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Sound extends Service {
    MediaPlayer mediaPlayer;
    int id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ANH", "sound");
        String keyReceive = intent.getExtras().getString("status");
        Log.e("ANH", keyReceive);

        if(keyReceive.equals("on")){
            id = 1;
        }else if(keyReceive.equals("off")){
            id = 0;
        }
        //mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        if( id == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
            mediaPlayer.start();
            id = 0;
        } else if (id ==0){
            if(mediaPlayer!= null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }

        return START_NOT_STICKY;
    }
}
