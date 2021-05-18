package com.example.bpgapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "ChannelID";
    public static final String channelName = "Channel";
    private NotificationManager mManager;

    /**
     * Constructor. Creates the notification channel if the SDK and version codes are correct
     * @param base
     */
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    /**
     * Creates a notification channel with sepcified criteria
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.blue);
        getManager().createNotificationChannel(channel);
    }

    /**
     * Gets the manager keeping track of time and returns it to the user
     * @return the manager
     */
    public NotificationManager getManager(){
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * Builds the notification
     * @param title title of the notification
     * @param message content of the notification
     * @return the built notification
     */
    public NotificationCompat.Builder getChannelNotification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                //High Priority -> auto pop-up
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Defaults includes the sound
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.blood_drop);
    }
}
