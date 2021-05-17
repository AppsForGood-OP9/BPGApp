package com.example.bpgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Switch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//here too
public class bloodPressureEntry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Initialize variable
    EditText systolicNum;
    EditText diastolicNum;
    EditText notes;
    Button submit;
    TextView TimeText;
    List<bpData> dataList = new ArrayList<>();
    bpRoomDB bpDatabase;
    BPAdapter bpAdapter;
    int dayofmon;
    int mon;
    int yea;
    TextView date;
    TextView timeZone;
    Switch timeZoneSwitch;
    TextView hour;
    TextView minute;
    String currentDateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_entry);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_bp_blank, new SecondFragment()).commit();

        getSupportActionBar().setTitle("Blood Pressure Entry");

        //Assign variable
        hour = findViewById(R.id.hourEdit);
        minute = findViewById(R.id.minuteEdit);
        date = findViewById(R.id.DateText);

        systolicNum = findViewById(R.id.systolicNum);
        diastolicNum = findViewById(R.id.diastolicNum);
        notes = findViewById(R.id.notesEdit);
        submit = findViewById(R.id.submit);

        //Initialize database
        bpDatabase = bpRoomDB.getInstance(this);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();
        timeZone = (TextView) findViewById(R.id.ampmDisplay);
        timeZoneSwitch = (Switch) findViewById(R.id.ampmSwitch);
        //Initialize adapter
        bpAdapter = new BPAdapter(bloodPressureEntry.this, dataList);


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
        //Set adapter

        Button dateChanger = (Button) findViewById(R.id.dateButton);
        dateChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
               datePicker.show(getSupportFragmentManager(), "date picker");
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

        //Select star from table

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            Fragment selectedFragment = null;
            //Maybe do something here
            switch (item.getItemId()) {
                case R.id.firstFragment:
                    selectedFragment = new FirstFragment();
                    break;
                case R.id.secondFragment:
                    selectedFragment = new SecondFragment();
                    break;
                case R.id.thirdFragment:
                    selectedFragment = new ThirdFragment();
                    break;
            }
            // It will help to replace the
            // one fragment to other.

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_bp_blank, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dayofmon = c.get(Calendar.DAY_OF_MONTH);
        mon = c.get(Calendar.MONTH)+1;
        yea =  c.get(Calendar.YEAR);
        currentDateString = mon+"/"+dayofmon+"/"+yea;
               // DateFormat.getDateInstance().format(c.getTime());
        date = (TextView) findViewById(R.id.DateText);
        date.setText(currentDateString);
        //Hello
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void systolicLevelCheck(View v)  {
        EditText systolicNum = findViewById(R.id.systolicNum);
        String systolicString= systolicNum.getText().toString();
        if (systolicString.isEmpty())
            return;
        //Use on others if working
        double systolicDouble=Double.parseDouble(systolicString);
        if (systolicDouble > 180)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 700);
            toast.show();
        }
        else if (systolicDouble < 90)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 700);
            toast.show();
        }
    }

    public void diastolicLevelCheck(View v) {
        EditText diastolicNum = findViewById(R.id.diastolicNum);
        String diastolicString = diastolicNum.getText().toString();
        double diastolicDouble = Double.parseDouble(diastolicString);
        if (diastolicDouble > 120) {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 1150, 700);
            toast.show();
        }
        else if (diastolicDouble < 60)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 1150, 700);
            toast.show();
        }
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
        Intent intent = new Intent(this, MainActivityBlank.class);
        startActivity(intent);
    }

    public void goToLog(View v)  {
        Intent intent = new Intent(this, Table.class);
        startActivity(intent);
    }

    public void goToReminders(View v)  {
        Intent intent = new Intent(this, ThirdFragment.class);
        startActivity(intent);
    }
}