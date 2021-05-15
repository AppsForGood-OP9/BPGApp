package com.example.bpgapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    //Initialize variable
    EditText glucoseNum;
    EditText notes;
    Button submit;
    TextView TimeText;
    List<GData> dataList = new ArrayList<>();
    GRoomDB gDatabase;
    com.example.bpgapp.GAdapter gAdapter;
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
    // TODO: Rename and change types and number of parameters
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

        hour = view.findViewById(R.id.hourEdit2);
        minute = view.findViewById(R.id.minuteEdit2);
        date = view.findViewById(R.id.DateText);

        glucoseNum = view.findViewById(R.id.glucoseNum);
        notes = view.findViewById(R.id.notesEdit);
        submit = view.findViewById(R.id.submit);

        //Initialize database
        gDatabase = GRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = gDatabase.gDao().getAll();

        gAdapter = new GAdapter(getActivity(), dataList);
        //Store database value in data list
        dataList = gDatabase.gDao().getAll();
        timeZone = (TextView) view.findViewById(R.id.ampmDisplay2);
        timeZoneSwitch = (Switch) view.findViewById(R.id.ampmSwitch2);
        Button dateChanger = (Button) view.findViewById(R.id.dateButton2);
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
                String gText = glucoseNum.getText().toString().trim();
                String nText = notes.getText().toString().trim();

                //Check condition
                if (!gText.equals("") || !nText.equals("")) {
                    //When text is not empty
                    //Initialize main data
                    GData data = new GData();
                    //Set text on main data
                    String timeText = hour.getText() + ":" + minute.getText() + " " + timeZone.getText();
                    data.setTime(timeText);
                    data.setDate(currentDateString);
                    Log.v("talia","currentDateString stored from GFragment" + currentDateString);
                    data.setGlucoseText(gText);
                    data.setNotesText(nText);
                    //Insert text in database
                    gDatabase.gDao().insert(data);
                    //Clear edit text
                    hour.setText("");
                    minute.setText("");
                    //What should we do about the AM/PM here?
                    date.setText("");
                    glucoseNum.setText("");
                    notes.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(gDatabase.gDao().getAll());
                    //GAdapter.notifyDataSetChanged();
                    //Add method before to implement here

                    //We should display a Toast to notify the user as to whether or not the data was entered successfully
                }
            }
        });

        return view;
    }

    public void glucoseLevelCheck(View v) {
        EditText glucoseNum = v.findViewById(R.id.glucoseNum);
        String glucoseString = glucoseNum.getText().toString();
        double glucoseDouble = Double.parseDouble(glucoseString);
        if (glucoseDouble > 180) {
            Toast toast = Toast.makeText(getContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 10, 1000);
            toast.show();
        } else if (glucoseDouble < 100) {
            Toast toast = Toast.makeText(getContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 10, 1000);
            toast.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
}