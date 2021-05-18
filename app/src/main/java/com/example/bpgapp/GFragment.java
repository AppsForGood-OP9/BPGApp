package com.example.bpgapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The GFragment class corresponds to the glucose level entry page and stores the entered data: date, time,
 * glucose level, and notes.
 */
public class GFragment extends Fragment {
    //Initialize variables
    private EditText glucoseNum;
    private EditText notes;
    private Button submit;
    private List<GData> dataList = new ArrayList<>();
    private GRoomDB gDatabase;
    private com.example.bpgapp.GAdapter gAdapter;
    private int dayOfMon;
    private int mon;
    private int yea;
    private TextView date;
    private TextView title;
    private TextView gLevelTitle;
    private TextView timeTitle;
    private TextView timeZone;
    private Switch timeZoneSwitch;
    private TextView hour;
    private TextView minute;
    private TextView timeColon;
    private String currentDateString;
    private TextView percentZoom;
    private ImageButton small;
    private ImageButton large;
    private int zoom = 100;
    private Button dateChanger;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GFragment.
     */
    public static GFragment newInstance(String param1, String param2) {
        GFragment fragment = new GFragment();
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
        View view = inflater.inflate(R.layout.activity_glucose_entry, container, false);

        //Retrieve view objects from the xml
        hour = view.findViewById(R.id.hourEdit2);
        minute = view.findViewById(R.id.minuteEdit2);
        date = view.findViewById(R.id.DateText);
        timeTitle = view.findViewById(R.id.timeText2);
        gLevelTitle = view.findViewById(R.id.glucoseText);
        title = view.findViewById(R.id.gText);
        glucoseNum = view.findViewById(R.id.glucoseNum);
        notes = view.findViewById(R.id.notesEdit);
        submit = view.findViewById(R.id.submit);
        timeZone = view.findViewById(R.id.ampmDisplay2);
        timeZoneSwitch = view.findViewById(R.id.ampmSwitch2);
        small = view.findViewById(R.id.ZoomOutButton);
        large = view.findViewById(R.id.ZoomInButton);
        dateChanger = view.findViewById(R.id.dateButton2);
        percentZoom = view.findViewById(R.id.percentZoom);
        timeColon = view.findViewById(R.id.timeColon2);

        //Initialize database
        gDatabase = GRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = gDatabase.gDao().getAll();
        //Initialize adapter
        gAdapter = new GAdapter(getActivity(), dataList);

        //Sets the TextView above the date changer button to the current date as default
        Calendar c = Calendar.getInstance();
        dayOfMon = c.get(Calendar.DAY_OF_MONTH);
        mon = c.get(Calendar.MONTH)+1;
        yea =  c.get(Calendar.YEAR);
        currentDateString = mon+"/"+dayOfMon+"/"+yea;
        date = view.findViewById(R.id.DateText);
        date.setText(currentDateString);

        large.setOnClickListener(new View.OnClickListener(){
            /*
            Increases text size when plus sign magnifying button is clicked
             */
            @Override
            public void onClick(View v){
                title.setTextSize(21*(title.getTextSize())/40);
                gLevelTitle.setTextSize(21*(gLevelTitle.getTextSize())/40);
                glucoseNum.setTextSize(21*(glucoseNum.getTextSize())/40);
                date.setTextSize(21*(date.getTextSize())/40);
                dateChanger.setTextSize(21*(dateChanger.getTextSize())/40);
                timeTitle.setTextSize(21*(timeTitle.getTextSize())/40);
                hour.setTextSize(21*(hour.getTextSize())/40);
                minute.setTextSize(21*(minute.getTextSize())/40);
                timeColon.setTextSize(21*(timeColon.getTextSize())/40);
                minute.setTextSize(21*(minute.getTextSize())/40);
                timeZone.setTextSize(21*(timeZone.getTextSize())/40);
                submit.setTextSize(21*(submit.getTextSize())/40);

                zoom +=25;
                percentZoom.setText(zoom+"%");
            }
        });

        small.setOnClickListener(new View.OnClickListener(){
            @Override
            /*
            Decreases text size when minus sign magnifying button is clicked
             */
            public void onClick(View v){
                title.setTextSize(19*(title.getTextSize())/40);
                gLevelTitle.setTextSize(19*(gLevelTitle.getTextSize())/40);
                glucoseNum.setTextSize(19*(glucoseNum.getTextSize())/40);
                date.setTextSize(19*(date.getTextSize())/40);
                dateChanger.setTextSize(19*(dateChanger.getTextSize())/40);
                timeTitle.setTextSize(19*(timeTitle.getTextSize())/40);
                hour.setTextSize(19*(hour.getTextSize())/40);
                minute.setTextSize(19*(minute.getTextSize())/40);
                timeColon.setTextSize(19*(timeColon.getTextSize())/40);
                minute.setTextSize(19*(minute.getTextSize())/40);
                timeZone.setTextSize(19*(timeZone.getTextSize())/40);
                submit.setTextSize(19*(submit.getTextSize())/40);

                zoom -=25;
                percentZoom.setText(zoom+"%");
            }
        });

        //Sets the time in the hour and minute EditTexts to be the current time and sets the correct TimeZoneSwitch to
        //AM or PM
        //Adds a 0 in front of the minute if the current minute is at least :00 and at most :09
        //Changes the hour from a 24-hour system to a 12-hour system
        int currentHourInt = c.get(Calendar.HOUR_OF_DAY);
        int currentMinuteInt = (c.get(Calendar.MINUTE));
        String currentMinute = String.valueOf(currentMinuteInt);
        if (currentMinuteInt >= 0 && currentMinuteInt < 10)  {
            currentMinute = "0" + currentMinute;
        }
        if (currentHourInt > 12)  {
            currentHourInt = currentHourInt - 12;
        }
        String currentHour = String.valueOf(currentHourInt);
        hour.setText(currentHour, TextView.BufferType.EDITABLE);
        minute.setText(currentMinute,TextView.BufferType.EDITABLE);
        if (c.get(Calendar.AM_PM) == Calendar.PM) {
            timeZoneSwitch.setChecked(true);
            timeZone.setText("PM");
        }

        timeZoneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * If the switch is checked, the time zone is set to PM, and if the switch is not checked, the time zone
             * is set to AM
             * @param buttonView the switch button
             * @param isChecked if the button has been clicked and the switch moves to the other side
             */
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

        dateChanger.setOnClickListener(new View.OnClickListener() {
            /**
             * Displays the DatePickerFragment when the dateChanger button is clicked
             */
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            /**
             * When a user clicks the submit button, the entered data will be stored in the database. This includes
             * date, time, systolic level, diastolic level, and notes.
             */
            @Override
            public void onClick(View v) {
                //Get Strings from their respective EditTexts
                String gText = glucoseNum.getText().toString().trim();
                String nText = notes.getText().toString().trim();

                //Check condition
                if (!gText.equals("") || !nText.equals("")) {
                    //When text is not empty
                    //Initialize main data
                    GData data = new GData();

                    //Set text for each category of data in the database
                    currentDateString = (String) date.getText();
                    data.setDate(currentDateString);
                    String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();
                    data.setTime(timeText);
                    data.setGlucoseText(gText);
                    data.setNotesText(nText);

                    //Insert text in database
                    gDatabase.gDao().insert(data);

                    //Clear EditTexts and uncheck timeZoneSwitch
                    hour.setText("");
                    minute.setText("");
                    timeZoneSwitch.setChecked(false);
                    date.setText("");
                    glucoseNum.setText("");
                    notes.setText("");

                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(gDatabase.gDao().getAll());
                    gAdapter.notifyDataSetChanged();

                    //Display a Toast that the glucose data was entered successfully
                    Toast toast = Toast.makeText(getContext(), "Glucose data entered successfully.", Toast.LENGTH_LONG);
                    toast.show();

                    //Navigate back to Add Entry page
                    getParentFragmentManager().beginTransaction().replace(R.id.container_main_blank, new SecondFragment()).commit();
                }

            }
        });
        return view;
    }

}