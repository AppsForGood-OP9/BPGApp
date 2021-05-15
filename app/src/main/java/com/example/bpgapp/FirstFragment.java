package com.example.bpgapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bpgapp.ui.*;

//import com.example.bpgapp.ui.BPFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    private TableAdapter adapter;
    private bpRoomDB bpDatabase;
    private bpAdapter bpAdapter;
    private List<bpData> dataList = new ArrayList<>();

    private String dateStr, timeStr;
    private String systolicStr, diastolicStr, notesStr;

    //ImageView bpDelete;
    private TextView dateItem, timeItem;
    private TextView systolicItem, diastolicItem, notesItem;

    private RecyclerView table_recycler_view;
    private LinearLayoutManager linearLayoutManager;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_table, container, false);

        table_recycler_view = view.findViewById(R.id.table_recycler_view);

        setTableRecyclerView();

        dateItem = view.findViewById(R.id.dateItem);
        timeItem = view.findViewById(R.id.timeItem);
        systolicItem = view.findViewById(R.id.systolicItem);
        diastolicItem = view.findViewById(R.id.diastolicItem);
        notesItem = view.findViewById(R.id.notesItem);
        //bpDelete = findViewById(R.id.bp_delete);

        bpDatabase = bpRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new bpAdapter(getActivity(), dataList);

        //Set adapter
        table_recycler_view.setAdapter(bpAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
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

        // Inflate the layout for this fragment
        Log.d("TARICCO","FirstFragment - onCreateView() - inflate activity_table");


        table_recycler_view = view.findViewById(R.id.table_recycler_view);
        setTableRecyclerView();

        dateItem = view.findViewById(R.id.dateItem);
        timeItem = view.findViewById(R.id.timeItem);
        systolicItem = view.findViewById(R.id.systolicItem);
        diastolicItem = view.findViewById(R.id.diastolicItem);
        notesItem = view.findViewById(R.id.notesItem);
        //bpDelete = findViewById(R.id.bp_delete);

        bpDatabase = bpRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new bpAdapter(getActivity(), dataList);

        //Set adapter
        table_recycler_view.setAdapter(bpAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        table_recycler_view.setLayoutManager(linearLayoutManager);


        dateStr = data.getDate();
        timeStr = data.getTime();
        systolicStr = data.getSystolicText();
        diastolicStr = data.getDiastolicText();
        notesStr = data.getNotesText();

        //Remove these when editing code
        Log.v("talia","date = " + dateStr);
        Log.v("talia","time = " + timeStr);
        Log.v("talia", "systolicStr = " + systolicStr);

        Button bpToggle = view.findViewById(R.id.bloodPressureToggle);
        Button gToggle = view.findViewById(R.id.glucoseToggle);

        bpToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BPTableFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        gToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new GTableFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void setTableRecyclerView() {
        table_recycler_view.setHasFixedSize(true);
        table_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        //Changes here, might only need two arguments for TableAdapter
        adapter = new TableAdapter(getContext(), getList(), dataList);
        table_recycler_view.setAdapter(adapter);
    }

    private List<TableModel> getList()  {
        List<TableModel> table_list = new ArrayList<>();
        table_list.add(new TableModel(dateStr,timeStr,systolicStr,diastolicStr,notesStr));
        return table_list;
    }

}