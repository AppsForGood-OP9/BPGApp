package com.example.bpgapp;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTableFragment extends Fragment {

    private TableAdapterG adapter;
    private GRoomDB gDatabase;
    private GAdapter gAdapter;
    private List<GData> dataList = new ArrayList<>();

    private String dateStr, timeStr;
    private String glucoseStr, notesStr;

    //ImageView bpDelete;
    private TextView dateItem, timeItem;
    private TextView glucoseItem, notesItem;

    private RecyclerView table_recycler_view;
    private LinearLayoutManager linearLayoutManager;

    private Button yesButton;
    private Button noButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GTableFragment newInstance(String param1, String param2) {
        GTableFragment fragment = new GTableFragment();
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
        View view = inflater.inflate(R.layout.activity_table_g, container, false);
        table_recycler_view = view.findViewById(R.id.table_recycler_view);

        setTableRecyclerView();

        dateItem = view.findViewById(R.id.dateItem);
        timeItem = view.findViewById(R.id.timeItem);
        glucoseItem = view.findViewById(R.id.glucoseItem);
        notesItem = view.findViewById(R.id.notesItem);
        //bpDelete = findViewById(R.id.bp_delete);

        gDatabase = GRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = gDatabase.gDao().getAll();

        gAdapter = new GAdapter(getActivity(), dataList);

        //Set adapter
        table_recycler_view.setAdapter(gAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        table_recycler_view.setLayoutManager(linearLayoutManager);

        GData data = new GData();
        //Set text on main data

        dateStr = data.getDate();
        timeStr = data.getTime();
        glucoseStr = data.getGlucoseText();
        notesStr = data.getNotesText();

        //Remove these when editing code
        Log.v("talia","date = " + dateStr);
        Log.v("talia","time = " + timeStr);
        Log.v("talia", "glucoseStr = " + glucoseStr);

        dateItem = view.findViewById(R.id.dateItem);
        timeItem = view.findViewById(R.id.timeItem);
        glucoseItem = view.findViewById(R.id.glucoseItem);
        notesItem = view.findViewById(R.id.notesItem);
        //bpDelete = findViewById(R.id.bp_delete);

        gDatabase = GRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = gDatabase.gDao().getAll();

        gAdapter = new GAdapter(getActivity(), dataList);

        //Set adapter
        table_recycler_view.setAdapter(gAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        table_recycler_view.setLayoutManager(linearLayoutManager);


        dateStr = data.getDate();
        timeStr = data.getTime();
        glucoseStr = data.getGlucoseText();
        notesStr = data.getNotesText();

        //Remove these when editing code
        Log.v("talia","date = " + dateStr);
        Log.v("talia","time = " + timeStr);
        Log.v("talia", "systolicStr = " + glucoseStr);

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

        //Initialize item touch helper
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Create dialog
                Dialog dialog = new Dialog(getContext());
                //Set content view
                dialog.setContentView(R.layout.table_dialog_update);
                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width, height);
                //Show dialog
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                noButton = dialog.findViewById(R.id.no_button);
                yesButton = dialog.findViewById(R.id.yes_button);

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.cancel();

                        //Notify adapter
                        gAdapter.notifyDataSetChanged();
                        return;
                    }
                });

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When item swipe
                        //Get text from database
                        int position = viewHolder.getAdapterPosition();
                        GData d = dataList.get(position);
                        //Delete text from database
                        gDatabase.gDao().delete(d);
                        //Remove item from ArrayList
                        dataList.remove(position);

                        //Notify when data is deleted
                        gAdapter.notifyItemRemoved(position);
                        gAdapter.notifyItemRangeChanged(position, dataList.size());
                        //Notify adapter
                        gAdapter.notifyDataSetChanged();

                        dialog.cancel();
                    }
                });

                yesButton.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() {
                    @Override
                    public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
                        dialog.cancel();

                        //Notify adapter
                        gAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }
        }).attachToRecyclerView(table_recycler_view);

        return view;
    }

    private void setTableRecyclerView() {
        table_recycler_view.setHasFixedSize(true);
        table_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        //Changes here, might only need two arguments for TableAdapter
        adapter = new TableAdapterG(getContext(), getList(), dataList);
        table_recycler_view.setAdapter(adapter);
    }

    private List<TableModelG> getList()  {
        List<TableModelG> table_list = new ArrayList<>();
        table_list.add(new TableModelG(dateStr,timeStr,glucoseStr,notesStr));
        return table_list;
    }
}