package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class reminders extends AppCompatActivity {

    int hours;
    Button notifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        TextView timeText = findViewById(R.id.timeText);
        //Notification Code
        notifyBtn = findViewById(R.id.getNotified);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.v("Yiming", "Enters the if statement");
            String ChannelID = "Example";
            CharSequence name = "Trial Notification";
            String description = "Trial Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ChannelID, name, importance);
            //NotificationChannel channels = new NotificationChannel("Trial Notification", "Trial Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        notifyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Notification code goes here
                NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(reminders.this);
                nBuilder.setContentTitle("Reminder");
                nBuilder.setContentText("Take your measurement");
                nBuilder.setSmallIcon(R.drawable.bell_button_foreground);
                nBuilder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(reminders.this);
                managerCompat.notify(1, nBuilder.build());
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void addBloodPressureEntry(View v)  {
        Intent intent = new Intent(this, bloodPressureEntry.class);
        startActivity(intent);
    }

    public void addGlucoseEntry(View v)  {
        Intent intent = new Intent(this, glucoseEntry.class);
        startActivity(intent);
    }

    public void goToAddEntry(View v)  {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToLog(View v)  {
        Intent intent = new Intent(this, LogDisplay.class);
        startActivity(intent);
    }

    public void goToReminders(View v)  {
        Intent intent = new Intent(this, reminders.class);
        startActivity(intent);
    }

}