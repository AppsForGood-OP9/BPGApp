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
    /**
     *When the broadcast is received (meaning it's time to send the notification
     * @param context requited info when making a notification
     * @param intent requited info when making a notification
     */
    @Override
    public void onReceive(Context context, Intent intent){
        //Creates and sends a notfication
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("BPG reminder", "Remember to take your blood pressure measurement");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
