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
import android.widget.EditText;
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

    //Declare Data
    private Button notifyBtn;
    private final String CHANNEL_ID = "Channel_ID";

    private TextView percentZoom;
    private ImageButton large;
    private ImageButton small;
    private int zoom = 100;
    private TextView title;
    private Button pressureButton;
    private Button glucoseButton;
    private TextView dailyAlertsTitle;
    private EditText hour;
    private EditText minute;
    private TextView timeZone;
    private Button save;

    private TextView makeEntry;
    private int hours;
    private RecyclerView remindersRecyclerView;

    private Switch timeZoneSwitch;

    private List<RemindersData> remindersDataList = new ArrayList();
    private LinearLayoutManager linearLayoutManager;
    private RemindersRoomDB remindersDatabase;
    private RemindersAdapter remindersAdapter;
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
        View view = inflater.inflate(R.layout.activity_reminders, container, false);

        //Assign Variables
        percentZoom = (TextView) view.findViewById(R.id.percentZoom);
        large = (ImageButton) view.findViewById(R.id.ZoomInButton);
        small = (ImageButton) view.findViewById(R.id.ZoomOutButton);
        pressureButton = (Button) view.findViewById(R.id.bloodPressureToggle);
        glucoseButton = (Button) view.findViewById(R.id.bloodGlucoseToggle);
        title = (TextView) view.findViewById(R.id.remindersTitle);
        dailyAlertsTitle = (TextView) view.findViewById(R.id.alertsLabel);
        save = view.findViewById(R.id.save);
        hour = view.findViewById(R.id.hourEdit);
        minute = view.findViewById(R.id.minuteEdit);
        timeZone = (TextView) view.findViewById(R.id.ampmDisplay);


        remindersRecyclerView = view.findViewById(R.id.reminders_recycler_view);
        setRemindersRecyclerView();
        timeZoneSwitch = (Switch) view.findViewById(R.id.ampmSwitch);

        //Initialize database
        remindersDatabase = RemindersRoomDB.getInstance(getContext());


        //Store database value in data list
        remindersDataList = (List<RemindersData>) remindersDatabase.RemindersDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(getContext());

        //Set layout manager
        remindersRecyclerView.setLayoutManager(linearLayoutManager);

        //Initialize adapter
        remindersAdapter = new RemindersAdapter(getActivity(), remindersDataList);

        //Set adapter
        remindersRecyclerView.setAdapter(remindersAdapter);

        //If enlarge button is pressed
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressureButton.setTextSize(21 * (pressureButton.getTextSize()) / 40);
                glucoseButton.setTextSize(21 * (glucoseButton.getTextSize()) / 40);
                title.setTextSize(21 * (title.getTextSize()) / 40);
                dailyAlertsTitle.setTextSize(21 * (dailyAlertsTitle.getTextSize()) / 40);
                save.setTextSize(21 * (save.getTextSize()) / 40);
                hour.setTextSize(21 * (hour.getTextSize()) / 40);
                minute.setTextSize(21 * (minute.getTextSize()) / 40);
                timeZone.setTextSize(21 * (timeZone.getTextSize()) / 40);
                zoom += 25;
                percentZoom.setText(zoom + "%");
            }
        });
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressureButton.setTextSize(19 * (pressureButton.getTextSize()) / 40);
                glucoseButton.setTextSize(19 * (glucoseButton.getTextSize()) / 40);
                title.setTextSize(19 * (title.getTextSize()) / 40);
                dailyAlertsTitle.setTextSize(19 * (dailyAlertsTitle.getTextSize()) / 40);
                save.setTextSize(19 * (save.getTextSize()) / 40);
                hour.setTextSize(19 * (hour.getTextSize()) / 40);
                minute.setTextSize(19 * (minute.getTextSize()) / 40);
                timeZone.setTextSize(19 * (timeZone.getTextSize()) / 40);
                zoom -= 25;
                percentZoom.setText(zoom + "%");
            }
        });

        timeZoneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (timeZoneSwitch.isChecked()) {
                    timeZone.setText("PM");
                } else {
                    timeZone.setText("AM");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Yiming", "Save has been pressed");
                //Get string from edit text
                String hourText = hour.getText().toString();
                String minuteText = minute.getText().toString();
                String timeText = hourText + ":" + minuteText + " " + timeZone.getText().toString();

                //Check condition
                if (!hourText.equals("") && !minuteText.equals("")) {
                    String timeZoneText = timeZone.getText().toString();
                    //Log.d("Yiming", "Enters Third Fragment if statement");
                    //When text is not empty
                    //Initialize main data
                    RemindersData RemindersData = new RemindersData();
                    //Set text on main data
                    RemindersData.setTime(timeText);
                    //Insert text in database
                    remindersDatabase.RemindersDao().insert(RemindersData);
                    //Set alarm via Calendar and Alarm Manager

                    if (timeZoneText.equals("PM")){
                        hours = Integer.parseInt(hourText) + 12;
                    }
                    else if (timeZoneText.equals("AM") && hourText.equals("12"))  {
                        hours = 0;
                    }

                    else if (timeZoneText.equals("AM") && !hourText.equals("12")) {
                        hours = Integer.parseInt(hourText);
                    }


                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, hours);
                    c.set(Calendar.MINUTE, Integer.parseInt(minuteText));
                    c.set(Calendar.SECOND, 0);

                    if (c.getTimeInMillis() < System.currentTimeMillis()){
                        c.setTimeInMillis(c.getTimeInMillis()+24*60*60*1000);
                    }

                    startAlarm(c);

                    //Notify when data is inserted
                    remindersDataList.clear();
                    remindersDataList.addAll(remindersDatabase.RemindersDao().getAll());
                    remindersAdapter.notifyDataSetChanged();
                }
                //Clear edit text
                hour.setText("");
                minute.setText("");
                //timeZoneSwitch.setChecked(false);
            }
        });
    return view;
    }
        private void setRemindersRecyclerView () {
            remindersRecyclerView.setHasFixedSize(true);
            remindersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //Changes here, might only need two arguments for TableAdapter
            remindersAdapter = new RemindersAdapter(getActivity(), remindersDataList);
            remindersRecyclerView.setAdapter(remindersAdapter);
        }
    private void startAlarm(Calendar c) {
        System.out.println("Alarm has been started");
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);

        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}