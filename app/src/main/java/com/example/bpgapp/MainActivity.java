package com.example.bpgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    TextView makeEntry;
    TextView PercentZoom;
    Button PressureButton;
    Button GlucoseButton;
    ImageButton Large;
    ImageButton Small;
    ImageButton heart;
    ImageButton drop;
    ImageButton home;
    ImageButton plus;
    ImageButton bell;
    int zoom = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_blank, new FirstFragment()).commit();

        getSupportActionBar().setTitle("Add Entry");

        makeEntry = (TextView) findViewById(R.id.Prompt);
        PercentZoom = (TextView) findViewById(R.id.percentZoom);
        Large = (ImageButton) findViewById(R.id.ZoomInButton);
        Small = (ImageButton) findViewById(R.id.ZoomOutButton);
        PressureButton = (Button) findViewById(R.id.BloodPressureButton);
        GlucoseButton = (Button) findViewById(R.id.BloodGlucoseButton);
        heart = (ImageButton) findViewById(R.id.HeartSymbolButton);
        drop = (ImageButton) findViewById(R.id.BloodSymbolButton);
        home = (ImageButton) findViewById(R.id.HomeButton);
        plus = (ImageButton) findViewById(R.id.AddButton);
        bell = (ImageButton) findViewById(R.id.BellButton);

        Large.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(21*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(21*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(21*(GlucoseButton.getTextSize())/40);
                zoom +=25;
                PercentZoom.setText(zoom+"%");
            }
        });
        Small.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(19*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(19*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(19*(GlucoseButton.getTextSize())/40);
                zoom -=25;
                PercentZoom.setText(zoom+"%");
            }
        });

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
                    .replace(R.id.container_main_blank, selectedFragment)
                    .commit();

            return true;

        }
    };

    /**
    public void addBloodPressureEntry(View v)  {
        Log.v("talia", "addBPEntry in Main Activity clicked");
        Intent intent = new Intent(this, BPBlank.class);
        startActivity(intent);
    }
*/
    public void addGlucoseEntry(View v)  {
        Intent intent = new Intent(this, glucoseEntry.class);
        startActivity(intent);
    }

    public void goToAddEntry(View v)  {
        Intent intent = new Intent(this, MainActivity.class);
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