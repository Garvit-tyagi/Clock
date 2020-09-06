package com.example.clock;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class alarmFragment extends Fragment {


    private static final String TAG = "alarmFragment";
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private AlarmHelper alarmHelper;
    FloatingActionButton btn;
    private ArrayList<alarm_item> alarmList;

    public alarmFragment(){

    }

    //    private Calendar c1;
//    private String text;
//    private boolean check;
//    public static final String SHARED_PREFS="shared-prefs";
//    public static final String TEXT="text";
//    public static final String SWITCH_ON_OFF="switch_on_off";
//    public static final String ID="id";
//    public static  int id=1;
//      int hour;
//      int minute;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarms, container, false);
        getActivity().setTitle("Alarms");


        recyclerView=view.findViewById(R.id.alarm_recyclerView);
        btn = view.findViewById(R.id.floatingActionButton);
        alarmHelper=new AlarmHelper(getContext());
        alarmList=new ArrayList<>();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), newAlarm.class);
                startActivity(intent);
            }
        });

//        loadData();
//        Update();
//        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//        assert manager != null;
//        manager.cancel(1);
//        if (AlertReciever.mediaPlayer != null) {
//            AlertReciever.mediaPlayer.release();
//            btn.setChecked(false);
//        }
        return view;
    }
    @Override
    public void onResume() {
        viewAlarms();
        super.onResume();
    }
    private void viewAlarms() {
        Cursor cursor = alarmHelper.viewAlarm();
        alarmList.clear();
        TextView textView = requireView().findViewById(R.id.tv);
        if (cursor.getCount() == 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                do {
                    alarm_item alarmEntry = new alarm_item(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2));
                    alarmList.add(alarmEntry);
                } while (cursor.moveToNext());
            }
            cursor.close();
            alarmHelper.close();
            alarmAdapter = new AlarmAdapter(getContext(), alarmList);
            recyclerView.setAdapter(alarmAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

//    private void cancelAlarm() {
//        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(getContext(), AlertReciever.class);
//        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 1, i, 0);
//        alarmManager.cancel(pi);
//        Toast.makeText(getContext(), "Alarm Cancelled", Toast.LENGTH_SHORT);
//    }
//      @Override
//      public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
//        Log.i(TAG,"alarm");
//          Calendar calendar=Calendar.getInstance();
//          calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//          calendar.set(Calendar.MINUTE,minute);
//          calendar.set(Calendar.SECOND,0);
//          updateTimeText(calendar);
//          c1=calendar;
//
//
//      }
//      private void updateTimeText(Calendar c){
//        String timeText= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//        tv.setText(timeText);
//      }
//      private void startAlarm(Calendar c){
//        if(c!=null){
//          AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//          Intent i=new Intent(getContext(),AlertReciever.class);
//          PendingIntent pi=PendingIntent.getBroadcast(getContext(),1,i,0);
//          alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);}
//        else{
//            Toast.makeText(getContext(),"SET TIME",Toast.LENGTH_SHORT);
//        }

//      }


//      public void saveData(){
//        Log.i(TAG,"saved");
//          SharedPreferences sharedPreferences= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
//          SharedPreferences.Editor editor=sharedPreferences.edit();
//          editor.putString(TEXT,tv.getText().toString());
//          editor.putBoolean(SWITCH_ON_OFF,btn.isChecked());
//          editor.putInt(ID,id);
//          editor.apply();
//          Log.i(TAG,tv.getText().toString());
//      }
//      public void loadData(){
//        Log.i(TAG,"loaded");
//
//          SharedPreferences sharedPreferences= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
//          text=sharedPreferences.getString(TEXT,"00:00");
//          check=sharedPreferences.getBoolean(SWITCH_ON_OFF,false);
//          id=sharedPreferences.getInt(ID,1);
//          Log.i(TAG,text);
//
//
//      }
//      public void Update(){
//        Log.i(TAG,"updated");
//        tv.setText(text);
//        btn.setChecked(check);
//      }
//
//
//
//      @Override
//      public void onDestroy() {
//
//          super.onDestroy();
//          saveData();
//      }


}
