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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BPTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BPTableFragment extends Fragment {

    private TableAdapter adapter;
    private bpRoomDB bpDatabase;
    private BPAdapter bpAdapter;
    private List<bpData> dataList = new ArrayList<>();

    private String dateStr, timeStr;
    private String systolicStr, diastolicStr, notesStr;

    //ImageView bpDelete;
    private TextView dateItem, timeItem;
    private TextView systolicItem, diastolicItem, notesItem;

    private RecyclerView table_recycler_view;
    private LinearLayoutManager linearLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BPTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BPTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BPTableFragment newInstance(String param1, String param2) {
        BPTableFragment fragment = new BPTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_table_b_p, container, false);
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

        bpAdapter = new BPAdapter(getActivity(), dataList);

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

        bpAdapter = new BPAdapter(getActivity(), dataList);

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