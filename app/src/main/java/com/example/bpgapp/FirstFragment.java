package com.example.bpgapp;

import android.app.Dialog;
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
 * The FirstFragment class corresponds to the data tables and displays the user's data.
 */
public class FirstFragment extends Fragment {
    //Initialize variables
    private TableAdapterBP adapter;
    private BPRoomDB bpDatabase;
    private BPAdapter bpAdapter;
    private List<BPData> dataList = new ArrayList<>();
    private String dateStr, timeStr;
    private String systolicStr, diastolicStr, notesStr;
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

        bpDatabase = BPRoomDB.getInstance(getContext());
        //Store database value in data list
        dataList = bpDatabase.bpDao().getAll();

        bpAdapter = new BPAdapter(getActivity(), dataList);

        //Set adapter
        table_recycler_view.setAdapter(bpAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        table_recycler_view.setLayoutManager(linearLayoutManager);

        BPData data = new BPData();
        //Set text on main data
        dateStr = data.getDate();
        timeStr = data.getTime();
        systolicStr = data.getSystolicText();
        diastolicStr = data.getDiastolicText();
        notesStr = data.getNotesText();

        table_recycler_view = view.findViewById(R.id.table_recycler_view);
        setTableRecyclerView();

        dateItem = view.findViewById(R.id.dateItem);
        timeItem = view.findViewById(R.id.timeItem);
        systolicItem = view.findViewById(R.id.systolicItem);
        diastolicItem = view.findViewById(R.id.diastolicItem);
        notesItem = view.findViewById(R.id.notesItem);

        bpDatabase = BPRoomDB.getInstance(getContext());
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

        Button bpToggle = view.findViewById(R.id.bloodPressureToggle);
        Button gToggle = view.findViewById(R.id.glucoseToggle);

        bpToggle.setOnClickListener(new OnClickListener() {
            /**
             * When the blood pressure toggle button is clicked, display the corresponding table fragment
             * @param v the view
             */
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
            /**
             * When the glucose toggle button is clicked, display the corresponding table fragment
             * @param v the view
             */
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

        /**
         * The item touch helper will help to perform the delete action when a table row is swiped
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * When the item touch helper is swiped to the right, the entry row will either delete (on yes button)
             * or remain (on yes button)
             * @param viewHolder the view holder
             * @param direction the direction, right
             */
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
                        BPData d = dataList.get(viewHolder.getAdapterPosition());
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

    /**
     * Set table recycler view to have a fixed size and correspond to a linear layout manager and adapter
     */
    private void setTableRecyclerView() {
        table_recycler_view.setHasFixedSize(true);
        table_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        //Changes here, might only need two arguments for TableAdapter
        adapter = new TableAdapterBP(getContext(), getList(), dataList);
        table_recycler_view.setAdapter(adapter);
    }

    /**
     * Gets the data list of table row entries
     * @return the list of table data
     */
    private List<TableModelBP> getList()  {
        List<TableModelBP> table_list = new ArrayList<>();
        table_list.add(new TableModelBP(dateStr,timeStr,systolicStr,diastolicStr,notesStr));
        return table_list;
    }

}