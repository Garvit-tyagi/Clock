package com.example.clock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AlarmAdapter";
    private ArrayList<alarm_item> alarmArrayList;

    private Context context;
    private Calendar c;
    private Calendar c2;
    int hour;
    int min;
    boolean clicked=false;

    public AlarmAdapter(Context context, ArrayList<alarm_item> list) {
        this.context = context;
        alarmArrayList = list;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmAdapter.AlarmViewHolder holder, final int position) {
        holder.timeText.setText(alarmArrayList.get(position).getTime());

        hour=newAlarm.getHour();
        min=newAlarm.getMin();
        c2=Calendar.getInstance();
        c2.set(Calendar.HOUR_OF_DAY,hour);
        c2.set(Calendar.MINUTE,min);
        c2.set(Calendar.SECOND,0);

        if (alarmArrayList.get(position).getAlarm_atatus().equals("true")) {
            holder.statusSwitch.setChecked(true);
        } else
            holder.statusSwitch.setChecked(false);


        holder.statusSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.statusSwitch.isChecked()) {

                    AlarmHelper alarmHelper = new AlarmHelper(context);
                    alarmHelper.updateAlarmStatus(alarmArrayList.get(position).getId(),"true");
                    startAlarm(Integer.parseInt(alarmArrayList.get(position).getId()),c2);


                } else {
                    AlarmHelper alarmHelper = new AlarmHelper(context);
                    alarmHelper.updateAlarmStatus(alarmArrayList.get(position).getId(),"false");
                    cancelAlarm(Integer.parseInt(alarmArrayList.get(position).getId()));
                    Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        holder.timeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clicked=true;
//                Calendar calendar = Calendar.getInstance();
//                hour = calendar.get(Calendar.HOUR_OF_DAY);
//                min = calendar.get(Calendar.MINUTE);
//                TimePickerDialog timePicker = new TimePickerDialog(context, (TimePickerDialog.OnTimeSetListener) context, hour, min,
//                        DateFormat.is24HourFormat(context));
//                timePicker.show();
//                Log.i(TAG,"after show");
//            }
//        });
//        Log.i(TAG,"okay");

//        if(clicked){
//
//        String t=java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.getTime());
//        holder.timeText.setText(t);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Press and hold to Delete", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Delete ...");
                dialog.setMessage("Are you sure you want to delete ?");
                dialog.setIcon(android.R.drawable.ic_menu_delete);
                dialog.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                AlarmHelper alarmHelper = new AlarmHelper(context);
                                if (alarmHelper.getAlarmStatus(alarmArrayList.get(position).getId()).equals("true")) {
                                    cancelAlarm(Integer.parseInt(alarmArrayList.get(position).getId()));
                                }
                                alarmHelper.deleteAlarm(alarmArrayList.get(position));
                                alarmArrayList.remove(alarmArrayList.get(position));
                                notifyDataSetChanged();
                            }
                        });
                dialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                AlertDialog alertDialog = dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                return true;
            }
        });
    }

    private void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    private void startAlarm(int id,Calendar calendar) {
        if(calendar!=null){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReciever.class);



        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
}
    }

    @Override
    public int getItemCount() {
        return alarmArrayList.size();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                  Calendar calendar=Calendar.getInstance();
          calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
          calendar.set(Calendar.MINUTE,minute);
          calendar.set(Calendar.SECOND,0);

          c=calendar;
Log.i(TAG,"after time set");

      }


    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch statusSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.timeText);
            statusSwitch = itemView.findViewById(R.id.switch_on_off);
        }
    }
}
