package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class bloodPressureEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_entry);
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
        double systolicDouble=Double.parseDouble(systolicString);
        if (systolicDouble > 180)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 800);
            toast.show();
        }
        else if (systolicDouble < 90)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 800);
            toast.show();
        }
    }

    public void diastolicLevelCheck(View v) {
        EditText diastolicNum = findViewById(R.id.diastolicNum);
        String diastolicString = diastolicNum.getText().toString();
        double diastolicDouble = Double.parseDouble(diastolicString);
        if (diastolicDouble > 120) {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 1150, 800);
            toast.show();
        }
        else if (diastolicDouble < 60)  {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.START, 10, 800);
            toast.show();
        }
    }
}