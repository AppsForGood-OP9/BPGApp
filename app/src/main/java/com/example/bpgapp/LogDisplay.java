package com.example.bpgapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bpgapp.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LogDisplay extends AppCompatActivity {

    //Initialize variable
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataListOld = new ArrayList<>();
    List<bpData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    bpRoomDB bpDatabase;
    BPAdapter bpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        //getSupportActionBar().setTitle("Log");
        //Not sure why the log does not have a title

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        //Initialize database
        bpDatabase = bpRoomDB.getInstance(this);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setContentView(R.layout.fragment_log_display);

        //Assign variable
        editText = findViewById(R.id.edit_text);
        //btAdd = findViewById(R.id.bt_add);
        //btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.bp_recycler_view);

        //Initialize database
        //I believe this is where the data is retrieved
        bpDatabase = bpRoomDB.getInstance(this);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        bpAdapter = new BPAdapter(LogDisplay.this, dataList);
        //Set adapter
        recyclerView.setAdapter(bpAdapter);

        /*btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get string from edit text
                //Maybe make a query here?
                String sText = editText.getText().toString().trim();
                //Check condition
                //Do we have to do this for each object?
                if (!sText.equals(""))  {
                    //When text is not empty
                    //Initialize main data
                    bpData data = new bpData();
                    //Set text on main data
                    data.setSystolicText(sText);
                    //Insert text in database
                    bpDatabase.bpDao().insert(data);
                    //Clear edit text
                    editText.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(bpDatabase.bpDao().getAll());
                    bpAdapter.notifyDataSetChanged();
                }

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all data from database
                bpDatabase.bpDao().reset(dataList);
                //Notify when all data deleted
                dataList.clear();
                dataList.addAll(bpDatabase.bpDao().getAll());
                bpAdapter.notifyDataSetChanged();
            }
        });

         */

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
        Intent intent = new Intent(this, Table.class);
        startActivity(intent);
    }

    public void goToReminders(View v)  {
        Intent intent = new Intent(this, ThirdFragment.class);
        startActivity(intent);
    }
}