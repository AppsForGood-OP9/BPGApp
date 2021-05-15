package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.PendingIntent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class reminders extends AppCompatActivity {
    //Initialize variable
    Button save;
    RecyclerView RemindersRecyclerView;
    TextView timeZone;
    Switch timeZoneSwitch;
    TextView hour;
    TextView minute;
    Button notifyBtn;
    private final String CHANNEL_ID = "Channel_ID";

    List<RemindersData> RemindersDataList = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    RemindersRoomDB RemindersDatabase;
    RemindersAdapter RemindersAdapter;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        //Assign Variable
        save = findViewById(R.id.save);
        hour = findViewById(R.id.hourEdit);
        minute = findViewById(R.id.minuteEdit);
        RemindersRecyclerView = findViewById(R.id.reminders_recycler_view);
        timeZone = (TextView) findViewById(R.id.ampmDisplay);
        timeZoneSwitch = (Switch) findViewById(R.id.ampmSwitch);
        //Initialize database
        RemindersDatabase = RemindersRoomDB.getInstance(this);
        //Store database value in data list
        RemindersDataList = (List<RemindersData>) RemindersDatabase.RemindersDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        RemindersRecyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        RemindersAdapter = new RemindersAdapter(reminders.this, RemindersDataList);
        //Set adapter
        RemindersRecyclerView.setAdapter(RemindersAdapter);

        timeZoneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(timeZoneSwitch.isChecked()){
                    timeZone.setText("PM");
                }
                else{
                    timeZone.setText("AM");
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Yiming", "Save has been pressed");
                //Get string from edit text
                String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();

                //Check condition
                if (!timeText.equals("")) {
                    Log.d("Yiming", "Enters if statement");
                    //When text is not empty
                    //Initialize main data
                    RemindersData RemindersData = new RemindersData();
                    //Set text on main data
                    RemindersData.setTime(timeText);
                    //Insert text in database
                    RemindersDatabase.RemindersDao().insert(RemindersData);
                    //Clear edit text
                    hour.setText("");
                    minute.setText("");
                    //Notify when data is inserted
                    RemindersDataList.clear();
                    RemindersDataList.addAll(RemindersDatabase.RemindersDao().getAll());
                    RemindersAdapter.notifyDataSetChanged();
                }
            }
        });


        //Notification Code - Create the Notification Channel, then create the Notification on button click
        createNotificationChannel();
        notifyBtn = findViewById(R.id.getNotified);
        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();   // Making a separate method below for readability purposes
                // Set the alarm to start at approximately 2:00 p.m.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 11);
                calendar.set(Calendar.MINUTE, 20);

                // With setInexactRepeating(), you have to use one of the AlarmManager interval
                // constants--in this case, AlarmManager.INTERVAL_DAY.
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }
        });
    }
        private void addNotification () {
            Log.d("Reminders", "Calling Reminders.addNotification() method");
            // Use this constructor with two input parameters for Android 26+ support, second parameter is Channel ID
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.bell_button_foreground)
                    .setContentTitle("Reminders")
                    .setContentText("Take your measurement")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
        }
        private void createNotificationChannel () {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "TEST CHANNEL NAME"; //getString(R.string.channel_name);
                String description = "TEST CHANNEL DESCRIPTION";//getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
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