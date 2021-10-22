package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.List;

//Changes here
public class LineGraph extends AppCompatActivity {

    /*
    private GraphView graphView;
    private BPAdapter bpAdapter;
    private BPRoomDB bpDatabase;
    private List<BPData> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        graphView = (GraphView) findViewById(R.id.graph);

        bpDatabase = BPRoomDB.getInstance(getApplicationContext());
        //Changed to getApplicationContext() from getContext()
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new BPAdapter(getParent(), dataList);
        //Don't know if should be getParent() or getApplicationContext(); usually is getActivity()

        //grabData();

    }

     */
}