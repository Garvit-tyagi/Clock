package com.example.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private alarmFragment af;
    public static final String ID = "id";
    public static int id = 1;
    public static final String SHARED_PREFS="shared-prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnv = findViewById(R.id.bnv);
        af = new alarmFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, af).commit();

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment current = null;
                switch (menuItem.getItemId()) {
                    case R.id.alarm:
                        current = af;
                        break;
                    case R.id.stopwatch:
                        current = new stopwatchFragment();
                        break;
                    case R.id.timer:
                        current = new timerFragment();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, current).commit();
                return true;
            }
        });
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.ringtones_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                id = 1;
                Toast.makeText(this, "Ringtone 1 selected", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.two:
                id = 2;
                Log.i(TAG,""+id);
                Toast.makeText(this, "Ringtone 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.three:
                id = 3;
                Toast.makeText(this, "Ringtone 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.four:
                id = 4;
                Toast.makeText(this, "Ringtone 4 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.five:
                id = 5;
                Toast.makeText(this, "Ringtone 5 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                id = 1;
                return super.onOptionsItemSelected(item);
        }
    }
    public  void saveData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(ID,id);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id=sharedPreferences.getInt(ID,1);
    }

    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
}