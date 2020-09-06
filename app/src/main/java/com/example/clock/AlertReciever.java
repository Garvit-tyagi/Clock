package com.example.clock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import androidx.core.app.NotificationManagerCompat;


public class AlertReciever extends BroadcastReceiver {
    private static final String TAG = "AlertReciever";
    public static MediaPlayer mediaPlayer;
    int reqcode;
    @Override
    public void onReceive(Context context, Intent intent) {
        reqcode=intent.getIntExtra("reqcode",reqcode);
        AlarmHelper ah=new AlarmHelper(context);
        ah.updateAlarmStatus(String.valueOf(reqcode),"false");
//        int mpID = intent.getIntExtra("mp_ID", R.raw.thunder_with_lightnings);
        Log.i(TAG,""+MainActivity.id);
        if (mediaPlayer == null) {
            if(MainActivity.id==1){
            mediaPlayer = MediaPlayer.create(context,R.raw.thunder_with_lightnings);
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


        Intent intent1 = new Intent(context, openNotification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, "Alarm")
                .setSmallIcon(R.drawable.ic_alarm).setContentTitle("Alarm...")
                .setPriority(NotificationCompat.PRIORITY_HIGH).setContentText("Tap to cancel").
                        setContentIntent(pendingIntent).build();

        notificationManagerCompat.notify(1, notification);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
//        NotificationHelper notificationHelper = new NotificationHelper(context);
//        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
//        notificationHelper.getManager().notify(1, nb.build());
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mediaPlayer.start();
//            }
//        });
    }





    }

