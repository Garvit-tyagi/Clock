package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class openNotification extends AppCompatActivity {
    private static final String TAG = "openNotification";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    int shakeCount=0;
    TextView tv;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_notification);
        tv=findViewById(R.id.count);
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                shakeCount=shakeCount+1;
                Log.i(TAG,""+shakeCount+"");
                handleShakeEvent(count);
            }
        });
        button=findViewById(R.id.disable_alarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                assert manager != null;
                manager.cancel(1);
                AlertReciever.mediaPlayer.release();
                Toast.makeText(openNotification.this, "Alarm Disabled...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void handleShakeEvent(int c){
        if(shakeCount>=10){
            button.setVisibility(View.VISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }else{
            int i=10-shakeCount;
            tv.setText("SHAKES LEFT :"+i);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
