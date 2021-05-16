package com.example.bpgapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {
    Button save;
    private RecyclerView RemindersRecyclerView;
    TextView timeZone;
    Switch timeZoneSwitch;
    TextView hour;
    TextView minute;
    Button notifyBtn;
    private final String CHANNEL_ID = "Channel_ID";

    TextView PercentZoom;
    ImageButton Large;
    ImageButton Small;
    int zoom = 100;
    TextView makeEntry;
    ImageButton heart;
    ImageButton drop;
    ImageButton home;
    ImageButton plus;
    ImageButton bell;
    Button PressureButton;
    Button GlucoseButton;

    List<RemindersData> RemindersDataList = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    RemindersRoomDB RemindersDatabase;
    RemindersAdapter RemindersAdapter;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        Log.d("TARICCO","ThirdFragment - onCreateView() - inflate activity_reminders");
        View view = inflater.inflate(R.layout.activity_reminders, container, false);
        //Assign Variable

        makeEntry = (TextView) view.findViewById(R.id.Prompt);
        PercentZoom = (TextView) view.findViewById(R.id.percentZoom);
        Large = (ImageButton) view.findViewById(R.id.ZoomInButton);
        Small = (ImageButton) view.findViewById(R.id.ZoomOutButton);
        PressureButton = (Button) view.findViewById(R.id.BloodPressureButton);
        GlucoseButton = (Button) view.findViewById(R.id.BloodGlucoseButton);
        heart = (ImageButton) view.findViewById(R.id.HeartSymbolButton);
        drop = (ImageButton) view.findViewById(R.id.BloodSymbolButton);
        home = (ImageButton) view.findViewById(R.id.HomeButton);
        plus = (ImageButton) view.findViewById(R.id.AddButton);
        bell = (ImageButton) view.findViewById(R.id.BellButton);
        save = view.findViewById(R.id.save);
        hour = view.findViewById(R.id.hourEdit);
        minute = view.findViewById(R.id.minuteEdit);
        RemindersRecyclerView = view.findViewById(R.id.reminders_recycler_view);
        setRemindersRecyclerView();
        timeZone = (TextView) view.findViewById(R.id.ampmDisplay);
        timeZoneSwitch = (Switch) view.findViewById(R.id.ampmSwitch);
        //Initialize database
        RemindersDatabase = RemindersRoomDB.getInstance(getContext());
        //Store database value in data list
        RemindersDataList = (List<RemindersData>) RemindersDatabase.RemindersDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(getContext());
        //Set layout manager
        RemindersRecyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        RemindersAdapter = new RemindersAdapter(getActivity(), RemindersDataList);
        //Set adapter
        RemindersRecyclerView.setAdapter(RemindersAdapter);

        Large.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(21*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(21*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(21*(GlucoseButton.getTextSize())/40);
                zoom +=25;
                PercentZoom.setText(zoom+"%");
            }
        });
        Small.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(19*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(19*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(19*(GlucoseButton.getTextSize())/40);
                zoom -=25;
                PercentZoom.setText(zoom+"%");
            }
        });

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
        notifyBtn = view.findViewById(R.id.getNotified);
        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();   // Making a separate method below for readability purposes
                // Set the alarm to start at approximately 2:00 p.m.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 11);
                calendar.set(Calendar.MINUTE, 20);
                Context context = getContext();

                // With setInexactRepeating(), you have to use one of the AlarmManager interval
                // constants--in this case, AlarmManager.INTERVAL_DAY.

                //This is statement is what Talia changed, not sure if it is right
                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }

            private void addNotification() {
                Log.d("Reminders", "Calling Reminders.addNotification() method");
                // Use this constructor with two input parameters for Android 26+ support, second parameter is Channel ID
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.bell_button_foreground)
                        .setContentTitle("Reminders")
                        .setContentText("Take your measurement")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                // Add as notification
                NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());
            }
        });
    return view;
    }

    private void setRemindersRecyclerView() {
        RemindersRecyclerView.setHasFixedSize(true);
        RemindersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Changes here, might only need two arguments for TableAdapter
        RemindersAdapter = new RemindersAdapter(getActivity(), RemindersDataList);
        RemindersRecyclerView.setAdapter(RemindersAdapter);
    }
/*
    private List<RemindersModel> getList()  {
        List<RemindersModel> reminders_list = new ArrayList<>();
        reminders_list.add(new RemindersModel(time));
        return reminders_list;
    }
    */


    private void createNotificationChannel() {
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
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}