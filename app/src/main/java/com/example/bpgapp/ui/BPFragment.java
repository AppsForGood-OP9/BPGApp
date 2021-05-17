package com.example.bpgapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpgapp.DatePickerFragment;
import com.example.bpgapp.R;
import com.example.bpgapp.BPAdapter;
import com.example.bpgapp.SecondFragment;
import com.example.bpgapp.bpData;
import com.example.bpgapp.bpRoomDB;

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
    private EditText notes;
    private Button submit;
    private List<bpData> dataList = new ArrayList<>();
    private bpRoomDB bpDatabase;
    private BPAdapter bpAdapter;
    private int dayofmon;
    private int mon;
    private int yea;
    private TextView date;
    private TextView timeZone;
    private Switch timeZoneSwitch;
    private EditText hour;
    private EditText minute;
    private String currentDateString;

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
        hour = view.findViewById(R.id.hourEdit);
        minute = view.findViewById(R.id.minuteEdit);
        date = view.findViewById(R.id.DateText);
        systolicNum = view.findViewById(R.id.systolicNum);
        diastolicNum = view.findViewById(R.id.diastolicNum);
        notes = view.findViewById(R.id.notesEdit);
        submit = view.findViewById(R.id.submit);
        timeZone = view.findViewById(R.id.ampmDisplay);
        timeZoneSwitch = view.findViewById(R.id.ampmSwitch);
        Button dateChanger = view.findViewById(R.id.dateButton);

        //Initialize database
        bpDatabase = bpRoomDB.getInstance(getContext());
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
                    bpData data = new bpData();

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