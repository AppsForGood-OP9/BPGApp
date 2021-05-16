package com.example.bpgapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("BPG reminder", "Remember to take your blood pressure measurement");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
