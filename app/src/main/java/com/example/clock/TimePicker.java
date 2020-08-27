package com.example.clock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker extends DialogFragment  {

    private TimePickerDialog.OnTimeSetListener mlistener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), mlistener,hour,minute,
                DateFormat.is24HourFormat(getActivity()));

    }



   public void setTimeListener( TimePickerDialog.OnTimeSetListener listener){
        mlistener=listener;

   }
}
