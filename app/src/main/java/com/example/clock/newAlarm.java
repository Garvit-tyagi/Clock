package com.example.clock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;



public class newAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public static final String SHARED_PREFS="shared-prefs";
    public static final String REQCODE="reqcode";
    SharedPreferences sharedPreferences;
    private static final String TAG = "newAlarm";
    private TextView alarm_tv;
    private Button set_alarm;
    AlarmHelper alarmHelper;
    private Calendar c=Calendar.getInstance();
    private int reqcode=0;
   static int extrhour;
    static int extramin;
     int hour;
    int min;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        alarmHelper=new AlarmHelper(this);
        alarm_tv=findViewById(R.id.timeset_tv);
        sharedPreferences=getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        set_alarm=findViewById(R.id.btn_setalarm);
        alarm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(newAlarm.this, (TimePickerDialog.OnTimeSetListener) newAlarm.this, hour, min,
                        DateFormat.is24HourFormat(newAlarm.this));
                timePicker.show();
            }
        });
        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time==null){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(newAlarm.this);
                    dialog.setMessage("");
                    dialog.setTitle("Select Time...");
                    dialog.setIcon(R.drawable.ic_block);
                    dialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                }
                            });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }else{
                    alarmHelper.addAlarm(time, "true");
                    startAlarm();
                    finish();
                }

            }
        });



    }
    private void startAlarm() {
        reqcode = reqcode+1;

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(REQCODE,reqcode).apply();
        Log.d(TAG, "startAlarm: reqCode" + reqcode);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
          intent.putExtra("reqcode",reqcode);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqcode, intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(newAlarm.this, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
 extrhour=hourOfDay;
 extramin=minute;
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);

    }
    private void updateTimeText(Calendar c){
         time= java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.getTime());
        alarm_tv.setText(time);
    }
    public static int getHour(){
        return extrhour;
    }
    public static int getMin(){
        return extramin;
    }

    @Override
    protected void onResume() {
        reqcode=sharedPreferences.getInt(REQCODE,0);
        super.onResume();
    }
}
