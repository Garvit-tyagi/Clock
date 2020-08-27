package com.example.clock;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
      int hour;
      int minute;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.alarms, container, false);
getActivity().setTitle("Alarms");


        tv = view.findViewById(R.id.timeText);
        btn=view.findViewById(R.id.switch_on_off);
        loadData();
        Update();

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
          AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
          Intent i=new Intent(getContext(),AlertReciever.class);
          PendingIntent pi=PendingIntent.getBroadcast(getContext(),1,i,0);
          alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);

      }
      private void cancelAlarm(){
          AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
          Intent i=new Intent(getContext(),AlertReciever.class);
          PendingIntent pi=PendingIntent.getBroadcast(getContext(),1,i,0);
          alarmManager.cancel(pi);
          Toast.makeText(getContext(),"Alarm Cancelled",Toast.LENGTH_SHORT);
      }

      public void saveData(){
          SharedPreferences sharedPreferences= getActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sharedPreferences.edit();
          editor.putString(TEXT,tv.getText().toString());
          editor.putBoolean(SWITCH_ON_OFF,btn.isChecked());
          editor.apply();
      }
      public void loadData(){
          SharedPreferences sharedPreferences= getActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
          text=sharedPreferences.getString(TEXT,"00:00");
          check=sharedPreferences.getBoolean(SWITCH_ON_OFF,false);

      }
      public void Update(){
        tv.setText(text);
        btn.setChecked(check);
      }

      @Override
      public void onDestroyView() {

          super.onDestroyView();
          saveData();
      }

      @Override
      public void onDestroy() {

          super.onDestroy();
          saveData();
      }
  }
