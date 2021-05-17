package com.example.bpgapp.ui;

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

import com.example.bpgapp.BPData;
import com.example.bpgapp.BPRoomDB;
import com.example.bpgapp.DatePickerFragment;
import com.example.bpgapp.R;
import com.example.bpgapp.BPAdapter;
import com.example.bpgapp.SecondFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The BPFragment class corresponds to the blood pressure entry page and stores the entered data: date, time,
 * systolic level, diastolic level, and notes.
 */
public class BPFragment extends Fragment {
    //Initialize variables
    private EditText systolicNum;
    private EditText diastolicNum;
    private TextView slant;
    private EditText notes;
    private Button submit;
    private List<BPData> dataList = new ArrayList<>();
    private BPRoomDB bpDatabase;
    private BPAdapter bpAdapter;
    private int dayofmon;
    private int mon;
    private int yea;
    private TextView date;
    private Button dateButton;
    private TextView timeZone;
    private TextView title;
    private TextView systolicTitle;
    private TextView diastolicTitle;
    private Switch timeZoneSwitch;
    private EditText hour;
    private EditText minute;
    private String currentDateString;
    private TextView PercentZoom;
    private TextView timeColon;
    private ImageButton Large;
    private ImageButton Small;
    private int zoom = 100;
    private TextView timeTitle;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public BPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BPFragment.
     */
    public static BPFragment newInstance(String param1, String param2) {
        BPFragment fragment = new BPFragment();
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
        View view = inflater.inflate(R.layout.activity_blood_pressure_entry, container, false);

        //Retrieve view objects from the xml
        hour = (EditText) view.findViewById(R.id.hourEdit);
        minute = (EditText) view.findViewById(R.id.minuteEdit);
        date = (TextView) view.findViewById(R.id.DateText);
        systolicNum = (EditText) view.findViewById(R.id.systolicNum);
        diastolicNum = (EditText) view.findViewById(R.id.diastolicNum);
        notes = view.findViewById(R.id.notesEdit);
        submit = view.findViewById(R.id.submit);
        timeZone = view.findViewById(R.id.ampmDisplay);
        timeZoneSwitch = view.findViewById(R.id.ampmSwitch);
        Button dateChanger = view.findViewById(R.id.dateButton);
        PercentZoom = (TextView) view.findViewById(R.id.percentZoom);
        Large = (ImageButton) view.findViewById(R.id.ZoomInButton);
        Small = (ImageButton) view.findViewById(R.id.ZoomOutButton);
        title = (TextView) view.findViewById(R.id.bpText);
        systolicTitle = (TextView) view.findViewById(R.id.systolicText);
        diastolicTitle = (TextView) view.findViewById(R.id.diastolicText);
        slant = (TextView) view.findViewById(R.id.forwardSlash);
        dateButton = (Button) view.findViewById(R.id.dateButton);
        timeTitle = (TextView) view.findViewById(R.id.timeText);
        timeColon = (TextView) view.findViewById(R.id.timeColon);

        //Initialize database
        bpDatabase = BPRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();
        //Initialize adapter
        bpAdapter = new BPAdapter(getActivity(), dataList);

        //Sets the TextView above the date changer button to the current date as default
        Calendar c = Calendar.getInstance();
        dayofmon = c.get(Calendar.DAY_OF_MONTH);
        mon = c.get(Calendar.MONTH)+1;
        yea =  c.get(Calendar.YEAR);
        currentDateString = mon+"/"+dayofmon+"/"+yea;
        date = view.findViewById(R.id.DateText);
        date.setText(currentDateString);

        Large.setOnClickListener(new View.OnClickListener(){
            /*
            Increases text size when plus sign magnifying button is clicked
             */
            @Override
            public void onClick(View v){
                title.setTextSize(21*(title.getTextSize())/40);
                diastolicTitle.setTextSize(21*(diastolicTitle.getTextSize())/40);
                systolicTitle.setTextSize(21*(systolicTitle.getTextSize())/40);
                slant.setTextSize(21*(slant.getTextSize())/40);
                diastolicNum.setTextSize(21*(diastolicNum.getTextSize())/40);
                systolicNum.setTextSize(21*(systolicNum.getTextSize())/40);
                dateButton.setTextSize(21*(dateButton.getTextSize())/40);
                date.setTextSize(21*(date.getTextSize())/40);
                timeTitle.setTextSize(21*(timeTitle.getTextSize())/40);
                timeColon.setTextSize(21*(timeColon.getTextSize())/40);
                hour.setTextSize(21*(hour.getTextSize())/40);
                minute.setTextSize(21*(minute.getTextSize())/40);
                timeZone.setTextSize(21*(timeZone.getTextSize())/40);
                submit.setTextSize(21*(submit.getTextSize())/40);

                zoom +=25;
                PercentZoom.setText(zoom+"%");
            }
        });

       Small.setOnClickListener(new View.OnClickListener(){
           /*
            Decreases text size when minus sign magnifying button is clicked
             */
           @Override
           public void onClick(View v){
               title.setTextSize(19*(title.getTextSize())/40);
               diastolicTitle.setTextSize(19*(diastolicTitle.getTextSize())/40);
               systolicTitle.setTextSize(19*(systolicTitle.getTextSize())/40);
               slant.setTextSize(19*(slant.getTextSize())/40);
               diastolicNum.setTextSize(19*(diastolicNum.getTextSize())/40);
               systolicNum.setTextSize(19*(systolicNum.getTextSize())/40);
               dateButton.setTextSize(19*(dateButton.getTextSize())/40);
               date.setTextSize(19*(date.getTextSize())/40);
               timeTitle.setTextSize(19*(timeTitle.getTextSize())/40);
               timeColon.setTextSize(19*(timeColon.getTextSize())/40);
               hour.setTextSize(19*(hour.getTextSize())/40);
               minute.setTextSize(19*(minute.getTextSize())/40);
               timeZone.setTextSize(19*(timeZone.getTextSize())/40);
               submit.setTextSize(19*(submit.getTextSize())/40);

               zoom -=25;
               PercentZoom.setText(zoom+"%");
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
                String sText = systolicNum.getText().toString().trim();
                String dText = diastolicNum.getText().toString().trim();
                String nText = notes.getText().toString().trim();

                //Check condition
                if (!sText.equals("") || !dText.equals("") || !nText.equals("")) {
                    //When text is not empty
                    //Initialize main data
                    BPData data = new BPData();

                    //Set text for each category of data in the database
                    currentDateString = (String) date.getText();
                    data.setDate(currentDateString);
                    String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();
                    data.setTime(timeText);
                    data.setSystolicText(sText);
                    data.setDiastolicText(dText);
                    data.setNotesText(nText);

                    //Insert text in database
                    bpDatabase.bpDao().insert(data);

                    //Clear EditTexts and uncheck timeZoneSwitch
                    hour.setText("");
                    minute.setText("");
                    timeZoneSwitch.setChecked(false);
                    date.setText("");
                    systolicNum.setText("");
                    diastolicNum.setText("");
                    notes.setText("");

                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(bpDatabase.bpDao().getAll());
                    bpAdapter.notifyDataSetChanged();

                    //Display a Toast that the blood pressure data was entered successfully
                    Toast toast = Toast.makeText(getContext(), "Blood pressure data entered successfully.", Toast.LENGTH_LONG);
                    toast.show();

                    //Navigate back to Add Entry page
                    getParentFragmentManager().beginTransaction().replace(R.id.container_main_blank, new SecondFragment()).commit();
                }
            }
        });
        return view;
    }

}