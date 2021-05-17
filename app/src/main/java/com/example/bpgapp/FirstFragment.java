package com.example.bpgapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
    private BPAdapter bpAdapter;
    private List<bpData> dataList = new ArrayList<>();

    private String dateStr, timeStr;
    private String systolicStr, diastolicStr, notesStr;

    //ImageView bpDelete;
    private TextView dateItem, timeItem;
    private TextView systolicItem, diastolicItem, notesItem;

    private RecyclerView table_recycler_view;
    private LinearLayoutManager linearLayoutManager;

    private Button yesButton;
    private Button noButton;

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

        bpToggle.setOnClickListener(new OnClickListener() {
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

        gToggle.setOnClickListener(new OnClickListener() {
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

                noButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                        //Notify adapter
                        bpAdapter.notifyDataSetChanged();
                    }
                });

                yesButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When item swipe
                        //Get text from database
                        bpData d = dataList.get(viewHolder.getAdapterPosition());
                        //Delete text from database
                        bpDatabase.bpDao().delete(d);
                        //Remove item from ArrayList
                        int position = viewHolder.getAdapterPosition();
                        dataList.remove(position);

                        //Notify when data is deleted
                        bpAdapter.notifyItemRemoved(position);
                        bpAdapter.notifyItemRangeChanged(position, dataList.size());
                        //Notify adapter
                        bpAdapter.notifyDataSetChanged();

                        //Cancel dialog when delete is complete
                        dialog.cancel();
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
        adapter = new TableAdapter(getContext(), getList(), dataList);
        table_recycler_view.setAdapter(adapter);
    }

    private List<TableModel> getList()  {
        List<TableModel> table_list = new ArrayList<>();
        table_list.add(new TableModel(dateStr,timeStr,systolicStr,diastolicStr,notesStr));
        return table_list;
    }

}