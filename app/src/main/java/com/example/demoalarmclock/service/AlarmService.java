package com.example.demoalarmclock.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.demoalarmclock.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String STOP_SERVICE="STOP_SERVICE";
    private AlarmTopBroadcast stopBroadcast;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        stopBroadcast = new AlarmTopBroadcast();
        IntentFilter intentFilter = new IntentFilter(STOP_SERVICE);
        registerReceiver(stopBroadcast,intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(stopBroadcast);
        super.onDestroy();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int onStartCommand(Intent intent, int flags, int startId) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        Notification.Builder builder =new Notification.Builder(this);
        builder.setContentTitle("Alarm");
        builder.setContentText("Ruzzzz");
        builder.setCustomContentView(remoteViews);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent stopIntent = new Intent(STOP_SERVICE);
        NotificationChannel channel = new NotificationChannel("alarm", "alarm", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        builder.setChannelId(channel.getId());
        Notification notification = builder.build();
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,stopIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop_notf,stopPendingIntent);
        mediaPlayer = MediaPlayer.create(this,R.raw.baothuc);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        startForeground(1,notification);
        return START_STICKY;
    }
    public class AlarmTopBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(STOP_SERVICE)){
                mediaPlayer.stop();
                mediaPlayer.reset();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancelAll();
                stopForeground(true);

            }
        }
    }
}
