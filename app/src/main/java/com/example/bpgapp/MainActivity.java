package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


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
                makeEntry.setTextSize(35*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(35*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(35*(GlucoseButton.getTextSize())/40);
                zoom +=25;
                PercentZoom.setText(zoom+"%");
            }
        });
        Small.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(25*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(25*(PressureButton.getTextSize())/40);
                GlucoseButton.setTextSize(25*(GlucoseButton.getTextSize())/40);
                zoom -=25;
                PercentZoom.setText(zoom+"%");
            }
        });
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