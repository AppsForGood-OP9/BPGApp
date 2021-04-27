package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class glucoseEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose_entry);
    }

    public void glucoseLevelCheck(View v)  {
        EditText glucoseNum = findViewById(R.id.glucoseNum);
        String glucoseString= glucoseNum.getText().toString();
        double glucoseDouble=Double.parseDouble(glucoseString);
        if (glucoseDouble > 180)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 1000);
            toast.show();
        }
        else if (glucoseDouble < 100)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 1000);
            toast.show();
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //To be implemented later with Room API
    //Get the spinner from the xml
    //Spinner dropdown = findViewById(R.id.spinner1);
    //Create a list of items for the spinner
   // String[] items = new String[]{"Before", "After"};
    //Create an adapter to describe how the items are displayed, adapters are used in several places in android
    //There are multiple variations of this, but this is the basic variant
    //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

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