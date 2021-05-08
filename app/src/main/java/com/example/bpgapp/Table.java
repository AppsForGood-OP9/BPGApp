package com.example.bpgapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Table extends AppCompatActivity {

    RecyclerView table_recycler_view;
    TableAdapter adapter;

    bpRoomDB bpDatabase;
    List<bpData> dataList = new ArrayList<>();
    bpAdapter bpAdapter;
    String dateStr;
    String timeStr;
    String systolicStr;
    String diastolicStr;
    String notesStr;

    TextView dateItem;
    TextView timeItem;
    TextView systolicItem;
    TextView diastolicItem;
    TextView notesItem;

    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        table_recycler_view = findViewById(R.id.table_recycler_view);

        setTableRecyclerView();

        dateItem = findViewById(R.id.dateItem);
        timeItem = findViewById(R.id.timeItem);
        systolicItem = findViewById(R.id.systolicItem);
        diastolicItem = findViewById(R.id.diastolicItem);
        notesItem = findViewById(R.id.notesItem);

        bpDatabase = bpRoomDB.getInstance(this);
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new bpAdapter(Table.this, dataList);

        //Set adapter
        table_recycler_view.setAdapter(bpAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        table_recycler_view.setLayoutManager(linearLayoutManager);

        bpData data = new bpData();
        //Set text on main data

        dateStr = data.getDate();
        timeStr = data.getTime();
        systolicStr = data.getSystolicText();
        diastolicStr = data.getDiastolicText();
        notesStr = data.getNotesText();

        //Remove these when editing code
        Log.v("talia","date = " + dateStr);
        Log.v("talia","time = " + timeStr);
        Log.v("talia", "systolicStr = " + systolicStr);
    }

    private void setTableRecyclerView() {
        table_recycler_view.setHasFixedSize(true);
        table_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TableAdapter(this, getList());
        table_recycler_view.setAdapter(adapter);
    }

    private List<TableModel> getList()  {
        List<TableModel> table_list = new ArrayList<>();
        table_list.add(new TableModel(dateStr,timeStr,systolicStr,diastolicStr,notesStr));
        return table_list;
    }
}