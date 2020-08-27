package com.example.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnv=findViewById(R.id.bnv);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new alarmFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment current =null;
                switch(menuItem.getItemId()){
                    case R.id.alarm:
                        current =new alarmFragment();
                        break;
                    case R.id.stopwatch:
                        current =new stopwatchFragment();
                        break;
                    case R.id.timer:
                        current=new timerFragment();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,current).commit();
                return true;
            }
        });
    }
}
