package com.example.bpgapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The BPAdapter class handles the adapter for the blood pressure recycler view.
 */
public class BPAdapter extends RecyclerView.Adapter<BPAdapter.ViewHolder> {

    //Initialize variables
    private List<BPData> bpDataList;
    private Activity context;

    //Create constructor
    public BPAdapter(Activity context, List<BPData> dataList)  {
        this.context = context;
        this.bpDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bp_list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize blood pressure data
        BPData data = bpDataList.get(position);

        //Retrieve text from the database and set it in its respective TextView
        holder.bpTimeTextView.setText(data.getTime());
        holder.bpDateTextView.setText(data.getDate());
        holder.bpSystolicTextView.setText(data.getSystolicText());
        holder.bpDiastolicTextView.setText(data.getDiastolicText());
        holder.bpNotesTextView.setText(data.getNotesText());
    }

    /**
     * Gets the number of items in the database
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return bpDataList.size();
    }

    /**
     * The ViewHolder class creates a RecyclerView object
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variables
        TextView bpDateTextView;
        TextView bpTimeTextView;
        TextView bpSystolicTextView;
        TextView bpDiastolicTextView;
        TextView bpNotesTextView;

        /**
         * The ViewHolder constructor assigns variables used in the BPAdapter class to their xml equivalents
         * @param itemView the view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            bpDateTextView = itemView.findViewById(R.id.bp_date_text_view);
            bpTimeTextView = itemView.findViewById(R.id.bp_time_text_view);
            bpSystolicTextView = itemView.findViewById(R.id.bp_systolic_text_view);
            bpDiastolicTextView = itemView.findViewById(R.id.bp_diastolic_text_view);
            bpNotesTextView = itemView.findViewById(R.id.bp_notes_text_view);
        }
    }
}