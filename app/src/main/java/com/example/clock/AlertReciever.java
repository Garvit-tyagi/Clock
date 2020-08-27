package com.example.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlertReciever extends BroadcastReceiver {
    private static final String TAG = "AlertReciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        //this method is called when our alarm is fired
        Log.i(TAG,"Done");
    }
}
