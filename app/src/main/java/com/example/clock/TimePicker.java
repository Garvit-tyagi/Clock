package com.example.clock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker extends DialogFragment {
    private Context context;
    Calendar c=Calendar.getInstance();
    int hour=c.get(Calendar.HOUR_OF_DAY);
    int minute=c.get(Calendar.MINUTE);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new TimePickerDialog(getContext(),(TimePickerDialog.OnTimeSetListener) context,hour,minute,
                DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context=context;
        super.onAttach(context);
    }
}
