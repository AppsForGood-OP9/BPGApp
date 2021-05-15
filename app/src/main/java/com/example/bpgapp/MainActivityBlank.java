package com.example.bpgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpgapp.ui.BPFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivityBlank extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    //Initialize variable
    Button save;
    RecyclerView RemindersRecyclerView;
    Button notifyBtn;
    private final String CHANNEL_ID = "Channel_ID";

    List<RemindersData> RemindersDataList = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    RemindersRoomDB RemindersDatabase;
    RemindersAdapter RemindersAdapter;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blank);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.secondFragment);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_blank, new SecondFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using their id.
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
                    .replace(R.id.container_main_blank, selectedFragment)
                    .commit();
            return true;
        }
    };

    public void addBloodPressureEntry(View v)  {
        Log.v("talia","AddBloodPressureEntry in Main Activity Blank clicked");
        Fragment selectedFragment = null;
                selectedFragment = new BPFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_blank, selectedFragment)
                .commit();

    }

    public void addGlucoseEntry(View v)  {
        Intent intent = new Intent(this, glucoseEntry.class);
        startActivity(intent);
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
        Log.v("talia","Result of getVisibleFragment(): " + getVisibleFragment());
        date = (TextView) findViewById(R.id.DateText);
        date.setText(currentDateString);
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivityBlank.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
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

    public void glucoseLevelCheck(View v) {
        EditText glucoseNum = findViewById(R.id.glucoseNum);
        String glucoseString = glucoseNum.getText().toString();
        double glucoseDouble = Double.parseDouble(glucoseString);
        if (glucoseDouble > 180) {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very high", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 10, 1000);
            toast.show();
        } else if (glucoseDouble < 100) {
            Toast toast = Toast.makeText(getApplicationContext(), "Warning: Very low", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.START, 10, 1000);
            toast.show();
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}