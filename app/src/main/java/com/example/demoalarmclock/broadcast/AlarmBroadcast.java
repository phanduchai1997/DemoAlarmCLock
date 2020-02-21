package com.example.demoalarmclock.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.demoalarmclock.service.AlarmService;


import static com.example.demoalarmclock.AlarmActivity.START_ALARM;


public class AlarmBroadcast extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(START_ALARM)){
            Intent intent1 = new Intent(context, AlarmService.class);
            context.startForegroundService(intent1);
        }
    }
}
