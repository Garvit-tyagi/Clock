package com.example.clock;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class alarmFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {


      TextView tv;
    private Switch btn;
    private static final String TAG = "alarmFragment";
    private Calendar c1;
    private String text;
    private boolean check;
    public static final String SHARED_PREFS="shared-prefs";
    public static final String TEXT="text";
    public static final String SWITCH_ON_OFF="switch_on_off";
    public static final String ID="id";
    public static  int id=1;
      int hour;
      int minute;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarms, container, false);
getActivity().setTitle("Alarms");


        tv = view.findViewById(R.id.timeText);
        btn=view.findViewById(R.id.switch_on_off);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        DialogFragment df=new TimePicker();
                ((TimePicker)df).setTimeListener(alarmFragment.this);
        df.show(getFragmentManager(),"time picker");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.isChecked()){

                    Toast.makeText(getContext(),"Alarm set",Toast.LENGTH_SHORT).show();
                    startAlarm(c1);
                }
                else{

                    cancelAlarm();
                    Toast.makeText(getContext(), "Alarm Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadData();
        Update();
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        assert manager != null;
        manager.cancel(1);
        if(AlertReciever.mediaPlayer!=null){
            AlertReciever.mediaPlayer.release();
        btn.setChecked(false);}
        return view;
    }


      @Override
      public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG,"alarm");
          Calendar calendar=Calendar.getInstance();
          calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
          calendar.set(Calendar.MINUTE,minute);
          calendar.set(Calendar.SECOND,0);
          updateTimeText(calendar);
          c1=calendar;


      }
      private void updateTimeText(Calendar c){
        String timeText= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tv.setText(timeText);
      }
      private void startAlarm(Calendar c){
        if(c!=null){
          AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
          Intent i=new Intent(getContext(),AlertReciever.class);
          PendingIntent pi=PendingIntent.getBroadcast(getContext(),1,i,0);
          alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);}
        else{
            Toast.makeText(getContext(),"SET TIME",Toast.LENGTH_SHORT);
        }

      }
      private void cancelAlarm(){
          AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
          Intent i=new Intent(getContext(),AlertReciever.class);
          PendingIntent pi=PendingIntent.getBroadcast(getContext(),1,i,0);
          alarmManager.cancel(pi);
          Toast.makeText(getContext(),"Alarm Cancelled",Toast.LENGTH_SHORT);
      }

      public void saveData(){
        Log.i(TAG,"saved");
          SharedPreferences sharedPreferences= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sharedPreferences.edit();
          editor.putString(TEXT,tv.getText().toString());
          editor.putBoolean(SWITCH_ON_OFF,btn.isChecked());
          editor.putInt(ID,id);
          editor.apply();
          Log.i(TAG,tv.getText().toString());
      }
      public void loadData(){
        Log.i(TAG,"loaded");

          SharedPreferences sharedPreferences= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
          text=sharedPreferences.getString(TEXT,"00:00");
          check=sharedPreferences.getBoolean(SWITCH_ON_OFF,false);
          id=sharedPreferences.getInt(ID,1);
          Log.i(TAG,text);


      }
      public void Update(){
        Log.i(TAG,"updated");
        tv.setText(text);
        btn.setChecked(check);
      }



      @Override
      public void onDestroy() {

          super.onDestroy();
          saveData();
      }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.one:
//                id=1;
//                Toast.makeText(getContext(),"Ringtone 1 selected",Toast.LENGTH_SHORT).show();
//
//                return  true;
//
//            case R.id.two:
//                id=2;
//                Toast.makeText(getContext(),"Ringtone 2 selected",Toast.LENGTH_SHORT).show();
//                return  true;
//            case R.id.three:
//                id=3;
//                Toast.makeText(getContext(),"Ringtone 3 selected",Toast.LENGTH_SHORT).show();
//                return  true;
//            case R.id.four:
//                id=4;
//                Toast.makeText(getContext(),"Ringtone 4 selected",Toast.LENGTH_SHORT).show();
//                return  true;
//            case R.id.five:
//                id=5;
//                Toast.makeText(getContext(),"Ringtone 5 selected",Toast.LENGTH_SHORT).show();
//                return  true;
//                default:
//                    id=1;
//                    return super.onOptionsItemSelected(item);
//        }
//
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        inflater.inflate(R.menu.ringtones_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}
