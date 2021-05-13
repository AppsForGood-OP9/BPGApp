package com.example.bpgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityBlank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_blank);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_blank, new FirstFragment()).commit();
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

    public void addBloodPressureEntry(View v)  {
        Intent intent = new Intent(this, bloodPressureEntry.class);
        startActivity(intent);
    }

    public void addGlucoseEntry(View v)  {
        Intent intent = new Intent(this, glucoseEntry.class);
        startActivity(intent);
    }
}