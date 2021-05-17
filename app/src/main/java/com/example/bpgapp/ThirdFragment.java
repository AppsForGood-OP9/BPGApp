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
    int hours;
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
        Log.d("TARICCO", "ThirdFragment - onCreateView() - inflate activity_reminders");
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

        Large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEntry.setTextSize(21 * (makeEntry.getTextSize()) / 40);
                PressureButton.setTextSize(21 * (PressureButton.getTextSize()) / 40);
                GlucoseButton.setTextSize(21 * (GlucoseButton.getTextSize()) / 40);
                zoom += 25;
                PercentZoom.setText(zoom + "%");
            }
        });
        Small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEntry.setTextSize(19 * (makeEntry.getTextSize()) / 40);
                PressureButton.setTextSize(19 * (PressureButton.getTextSize()) / 40);
                GlucoseButton.setTextSize(19 * (GlucoseButton.getTextSize()) / 40);
                zoom -= 25;
                PercentZoom.setText(zoom + "%");
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
                Log.d("Yiming", "Save has been pressed");
                //Get string from edit text
                String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();

                //Check condition
                if (!timeText.equals("")) {
                    Log.d("Yiming", "Enters Third Fragment if statement");
                    //When text is not empty
                    //Initialize main data
                    RemindersData RemindersData = new RemindersData();
                    //Set text on main data
                    RemindersData.setTime(timeText);
                    //Insert text in database
                    RemindersDatabase.RemindersDao().insert(RemindersData);
                    //Set alarm via Calendar and Alarm Manager
                    if (timeZone.getText()=="PM"){
                        hours = Integer.parseInt(String.valueOf(hour.getText())) + 12;
                    }
                    Calendar c = Calendar.getInstance();
                    Log.d("Yiming", "Calendar" + c.toString());
                    c.set(Calendar.HOUR_OF_DAY, hours);
                    c.set(Calendar.MINUTE, Integer.parseInt((String.valueOf(minute.getText()))));
                    c.set(Calendar.SECOND, 0);

                    startAlarm(c);
                    //Clear edit text
                    hour.setText("");
                    minute.setText("");
                    timeZoneSwitch.setChecked(false);
                    //Notify when data is inserted
                    RemindersDataList.clear();
                    RemindersDataList.addAll(RemindersDatabase.RemindersDao().getAll());
                    RemindersAdapter.notifyDataSetChanged();
                }
            }
        });
    return view;
    }
        private void setRemindersRecyclerView () {
            RemindersRecyclerView.setHasFixedSize(true);
            RemindersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //Changes here, might only need two arguments for TableAdapter
            RemindersAdapter = new RemindersAdapter(getActivity(), RemindersDataList);
            RemindersRecyclerView.setAdapter(RemindersAdapter);
        }
    private void startAlarm(Calendar c) {
        Log.v("Yiming","Calendar time: " + c.getTimeInMillis());
        System.out.println("Alarm has been started");
        Log.d("Yiming", "Alarm has been started");
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}