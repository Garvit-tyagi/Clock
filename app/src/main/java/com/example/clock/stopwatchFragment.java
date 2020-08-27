package com.example.clock;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class stopwatchFragment extends Fragment {
    private View view;
    private Button btn_start,btn_stop,btn_reset,btn_lap;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;
    private TextView tv;
    private ListView listView;
    private ArrayList<String > laps=new ArrayList<>();
    private static final String TAG = "stopwatchFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.stopwatch,container,false);
         getActivity().setTitle("Stopwatch");
    btn_start=view.findViewById(R.id.btn_start);
        btn_stop=view.findViewById(R.id.btn_stop);
        btn_reset=view.findViewById(R.id.btn_reset);
        btn_lap=view.findViewById(R.id.btn_lap);
        chronometer=view.findViewById(R.id.chronometer);
        listView=view.findViewById(R.id.lap_list);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (!running){
                     chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffSet);
                     chronometer.start();
                     running=true;
                 }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    chronometer.stop();
                    pauseOffSet=SystemClock.elapsedRealtime()-chronometer.getBase();
                    running=false;

                }

            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       chronometer.setBase(SystemClock.elapsedRealtime());
                       pauseOffSet=0;
                       laps.clear();
            }
        });
        btn_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (running){
                    long time=SystemClock.elapsedRealtime()-chronometer.getBase();
                    int h   = (int)(time /3600000);
                    int m = (int)(time - h*3600000)/60000;
                    int s= (int)(time - h*3600000- m*60000)/1000 ;
                    String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                laps.add(t);
                  listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,laps));
                }
                else Toast.makeText(getContext(),"Start the stopwatch first",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
