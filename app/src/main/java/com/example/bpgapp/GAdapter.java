package com.example.bpgapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GAdapter extends RecyclerView.Adapter<GAdapter.ViewHolder>{

    //Initialize variables
    private List<GData> gDataList;
    private Activity context;

    //Create constructor
    public GAdapter(Activity context, List<GData> dataList)  {
        this.context = context;
        this.gDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.g_list_row_main, parent, false);
        return new GAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GAdapter.ViewHolder holder, int position) {
        //Initialize glucose data
        GData data = gDataList.get(position);

        //Retrieve text from the database and set it in its respective TextView
        holder.gTimeTextView.setText(data.getTime());
        holder.gDateTextView.setText(data.getDate());
        holder.gGlucoseTextView.setText(data.getGlucoseText());
        holder.gNotesTextView.setText(data.getNotesText());
    }

    /**
     * Gets the number of items in the database
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return gDataList.size();
    }

    /**
     * The ViewHolder class creates a RecyclerView object
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variables
        private TextView gDateTextView;
        private TextView gTimeTextView;
        private TextView gGlucoseTextView;
        private TextView gNotesTextView;

        /**
         * The ViewHolder constructor assigns variables used in the BPAdapter class to their xml equivalents
         * @param itemView the view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            gDateTextView = itemView.findViewById(R.id.g_date_text_view);
            gTimeTextView = itemView.findViewById(R.id.g_time_text_view);
            gGlucoseTextView = itemView.findViewById(R.id.g_glucose_text_view);
            gNotesTextView = itemView.findViewById(R.id.g_notes_text_view);
        }
    }
}
