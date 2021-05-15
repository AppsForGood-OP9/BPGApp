package com.example.bpgapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpgapp.DatePickerFragment;
import com.example.bpgapp.R;
import com.example.bpgapp.TimePickerFragment;
import com.example.bpgapp.bloodPressureEntry;
import com.example.bpgapp.bpAdapter;
import com.example.bpgapp.bpData;
import com.example.bpgapp.bpRoomDB;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BPFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    //Initialize variable
    EditText systolicNum;
    EditText diastolicNum;
    EditText notes;
    Button submit;
    TextView TimeText;
    List<bpData> dataList = new ArrayList<>();
    bpRoomDB bpDatabase;
    com.example.bpgapp.bpAdapter bpAdapter;
    int dayofmon;
    int mon;
    int yea;
    TextView date;
    TextView UserTime;
    TextView timeZone;
    Switch timeZoneSwitch;
    TextView hour;
    TextView minute;
    String currentDateString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    // TODO: Rename and change types and number of parameters
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
        //return inflater.inflate(R.layout.fragment_b_p, container, false);
        View view = inflater.inflate(R.layout.activity_blood_pressure_entry, container, false);

        hour = view.findViewById(R.id.hourEdit);
        minute = view.findViewById(R.id.minuteEdit);
        date = view.findViewById(R.id.DateText);

        systolicNum = view.findViewById(R.id.systolicNum);
        diastolicNum = view.findViewById(R.id.diastolicNum);
        notes = view.findViewById(R.id.notesEdit);
        submit = view.findViewById(R.id.submit);

        //Initialize database
        bpDatabase = bpRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new bpAdapter(getActivity(), dataList);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();
        timeZone = (TextView) view.findViewById(R.id.ampmDisplay);
        timeZoneSwitch = (Switch) view.findViewById(R.id.ampmSwitch);
        Button dateChanger = (Button) view.findViewById(R.id.dateButton);
        //Initialize adapter

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

        dateChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get string from edit text
                String sText = systolicNum.getText().toString().trim();
                String dText = diastolicNum.getText().toString().trim();
                String nText = notes.getText().toString().trim();

                //Check condition
                if (!sText.equals("") || !dText.equals("") || !nText.equals("")) {
                    //When text is not empty
                    //Initialize main data
                    bpData data = new bpData();
                    //Set text on main data
                    String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();
                    data.setTime(timeText);
                    data.setDate(currentDateString);
                    data.setSystolicText(sText);
                    data.setDiastolicText(dText);
                    data.setNotesText(nText);
                    //Insert text in database
                    bpDatabase.bpDao().insert(data);
                    //Clear edit text
                    hour.setText("");
                    minute.setText("");
                    //What should we do about the AM/PM here?
                    date.setText("");
                    systolicNum.setText("");
                    diastolicNum.setText("");
                    notes.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(bpDatabase.bpDao().getAll());
                    bpAdapter.notifyDataSetChanged();

                    //We should display a Toast to notify the user as to whether or not the data was entered successfully
                }
            }
        });

        return view;
    }



    //@Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dayofmon = c.get(Calendar.DAY_OF_MONTH);
        mon = c.get(Calendar.MONTH)+1;
        yea =  c.get(Calendar.YEAR);
        currentDateString = mon+"/"+dayofmon+"/"+yea;
        DateFormat.getDateInstance().format(c.getTime());
        date = (TextView) view.findViewById(R.id.DateText);
        date.setText(currentDateString);
        //Hello
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "datePicker");
    }

    public void systolicLevelCheck(View view)  {
        EditText systolicNum = view.findViewById(R.id.systolicNum);
        String systolicString= systolicNum.getText().toString();
        if (systolicString.isEmpty())
            return;
        //Use on others if working
        double systolicDouble=Double.parseDouble(systolicString);
        if (systolicDouble > 180)  {
            Toast toast = Toast.makeText(getContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 700);
            toast.show();
        }
        else if (systolicDouble < 90)  {
            Toast toast = Toast.makeText(getContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 700);
            toast.show();
        }
    }

    public void diastolicLevelCheck(View view) {
        EditText diastolicNum = view.findViewById(R.id.diastolicNum);
        String diastolicString = diastolicNum.getText().toString();
        double diastolicDouble = Double.parseDouble(diastolicString);
        if (diastolicDouble > 120) {
            Toast toast = Toast.makeText(getContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 1150, 700);
            toast.show();
        }
        else if (diastolicDouble < 60)  {
            Toast toast = Toast.makeText(getContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 1150, 700);
            toast.show();
        }
    }
}