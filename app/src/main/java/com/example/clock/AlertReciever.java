package com.example.clock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;


public class AlertReciever extends BroadcastReceiver {
    private static final String TAG = "AlertReciever";
    public static MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        int mpID = intent.getIntExtra("mp_ID", R.raw.thunder_with_lightnings);
        if (mediaPlayer == null) {
            if(MainActivity.id==1){
            mediaPlayer = MediaPlayer.create(context,mpID);
            mediaPlayer.start();
            }
            else if(MainActivity.id==2){
                mediaPlayer = MediaPlayer.create(context,R.raw.action_epic);
                mediaPlayer.start();
            }
            else if(MainActivity.id==3){
                mediaPlayer = MediaPlayer.create(context,R.raw.landras_dream);
                mediaPlayer.start();
            }
            else if(MainActivity.id==4){
                mediaPlayer = MediaPlayer.create(context,R.raw.magical_cute_bells);
                mediaPlayer.start();
            }
            else if(MainActivity.id==5){
                mediaPlayer = MediaPlayer.create(context,R.raw.zvonok_starogo_telefona_freetone);
                mediaPlayer.start();
            }else
            {
                mediaPlayer = MediaPlayer.create(context,R.raw.zvonok_starogo_telefona_freetone);
                mediaPlayer.start();
            }
        }
        Toast.makeText(context, "alarm", Toast.LENGTH_SHORT).show();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }





    }

