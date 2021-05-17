package com.example.bpgapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        Log.v("Yiming","Gets into Alert Receiver");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("BPG reminder", "Remember to take your blood pressure measurement");
        Uri alarmSound =
                RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
        //MediaPlayer mp = MediaPlayer. create (getApplicationContext(), alarmSound);
        //mp.start();
        notificationHelper.getManager().notify(1, nb.build());
    }
}
