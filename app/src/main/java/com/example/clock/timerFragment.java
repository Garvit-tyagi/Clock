package com.example.clock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class timerFragment extends Fragment {
    private static long START_TIME_IN_MILLIS = 0;
    private EditText edit_hour, edit_minutes, edit_seconds;
    private TextView mtv;
    private Button btn_set, btn_pause, btn_cancel;
    private long timeLeft = START_TIME_IN_MILLIS;
    private boolean running;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer, container, false);
        getActivity().setTitle("Timer");
        btn_set = view.findViewById(R.id.btn_set);
        btn_pause = view.findViewById(R.id.btn_pause);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        edit_hour = view.findViewById(R.id.edit_hour);
        edit_minutes = view.findViewById(R.id.edit_minutes);
        edit_seconds = view.findViewById(R.id.edit_seconds);
        mtv = view.findViewById(R.id.timer_text);


        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edit_hour.getText().toString();

                String value2 = edit_minutes.getText().toString();

                String value3 = edit_seconds.getText().toString();


                if (!(value.isEmpty()|| value2.isEmpty()||value3.isEmpty())) {
                    edit_hour.setVisibility(View.INVISIBLE);
                    edit_minutes.setVisibility(View.INVISIBLE);
                    edit_seconds.setVisibility(View.INVISIBLE);

                    btn_set.setVisibility(View.INVISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_pause.setVisibility(View.VISIBLE);
                    Integer h = Integer.parseInt(value);
                    Integer m = Integer.parseInt(value2);
                    Integer s = Integer.parseInt(value3);
                    START_TIME_IN_MILLIS = Long.valueOf(h * 60 * 60 * 1000 + m * 60 * 1000 + s * 1000);
                    timeLeft = START_TIME_IN_MILLIS;
                    startTimer();
                } else {
                    Toast.makeText(getContext(), "Enter input", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    btn_pause.setText("Pause");
                    pauseTimer();
                } else {
                    btn_pause.setText("Resume");
                    startTimer();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDowntext();
            }

            @Override
            public void onFinish() {
                //when timer finishes
                running=false;
                Toast.makeText(getContext(),"finished",Toast.LENGTH_SHORT);
                edit_hour.setVisibility(View.VISIBLE);
                edit_minutes.setVisibility(View.VISIBLE);
                edit_seconds.setVisibility(View.VISIBLE);

                btn_set.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.INVISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);

            }
        }.start();
        running = true;
        btn_pause.setText("Pause");
    }

    private void updateCountDowntext() {
        int hour = (int) (timeLeft / 1000) / 60 / 60;
        int min = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String time = String.format(Locale.getDefault(), "%02d : %02d : %02d", hour, min, seconds);
        mtv.setText(time);


    }

    private void resetTimer() {
        edit_hour.setVisibility(View.VISIBLE);
        edit_minutes.setVisibility(View.VISIBLE);
        edit_seconds.setVisibility(View.VISIBLE);
        btn_set.setVisibility(View.VISIBLE);
        btn_pause.setVisibility(View.INVISIBLE);
        btn_cancel.setVisibility(View.INVISIBLE);
        pauseTimer();
        START_TIME_IN_MILLIS=0;
        timeLeft=START_TIME_IN_MILLIS;
        updateCountDowntext();

    }

    private void pauseTimer() {
countDownTimer.cancel();
running=false;
btn_pause.setText("Resume");
    }
}
