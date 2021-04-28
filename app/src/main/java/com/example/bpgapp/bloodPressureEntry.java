package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpgapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class bloodPressureEntry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Initialize variable
    EditText systolicNum;
    Button submit;
    RecyclerView bpRecyclerView;
    TextView TimeText;
    List<bpData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    bpRoomDB bpDatabase;
    bpAdapter bpAdapter;
    int dayofmon;
    int mon;
    int yea;
    TextView date;
    TextView UserTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_entry);

        //Assign variable
        systolicNum = findViewById(R.id.systolicNum);
        submit = findViewById(R.id.submit);
        bpRecyclerView = findViewById(R.id.bp_recycler_view);

        //Initialize database
        bpDatabase = bpRoomDB.getInstance(this);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        bpRecyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        bpAdapter = new bpAdapter(bloodPressureEntry.this, dataList);
        //Set adapter
        bpRecyclerView.setAdapter(bpAdapter);

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
                //Check condition
                if (!sText.equals(""))  {
                    Log.d("talia", "Entered if statement");
                    //When text is not empty
                    //Initialize main data
                    bpData data = new bpData();
                    //Set text on main data
                    data.setBpText(sText);
                    //Insert text in database
                    bpDatabase.bpDao().insert(data);
                    System.out.println("Systolic value stored: " + sText);
                    Log.d("talia", "Systolic value stored in bloodPressureEntry: " + sText);
                    //Clear edit text
                    systolicNum.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(bpDatabase.bpDao().getAll());
                    bpAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dayofmon = c.get(Calendar.DAY_OF_MONTH);
        mon = c.get(Calendar.MONTH)+1;
        yea =  c.get(Calendar.YEAR);
        String currentDateString = mon+"/"+dayofmon+"/"+yea;
               // DateFormat.getDateInstance().format(c.getTime());
        date = (TextView) findViewById(R.id.DateText);
        date.setText(currentDateString);
        //Hello
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
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