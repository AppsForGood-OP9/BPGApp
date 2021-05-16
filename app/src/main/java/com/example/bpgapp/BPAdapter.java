package com.example.bpgapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BPAdapter extends RecyclerView.Adapter<BPAdapter.ViewHolder> {

    //Initialize variables
    private List<bpData> bpDataList;
    private Activity context;

    //Create constructor
    public BPAdapter(Activity context, List<bpData> dataList)  {
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
        bpData data = bpDataList.get(position);

        //Retrieve text from the database and set it in its respective TextView
        holder.bpTimeTextView.setText(data.getTime());
        holder.bpDateTextView.setText(data.getDate());
        holder.bpSystolicTextView.setText(data.getSystolicText());
        holder.bpDiastolicTextView.setText(data.getDiastolicText());
        holder.bpNotesTextView.setText(data.getNotesText());

        //May need to add these methods back
        /*
        holder.bpEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                bpData d = bpDataList.get(holder.getAdapterPosition());
                //Get ID
                int sID = d.getID();
                //Get text
                String sText = d.getSystolicText();
                String dText = d.getDiastolicText();
                String nText = d.getNotesText();

                //Create dialog
                Dialog dialog = new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.bp_dialog_update);
                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width, height);
                //Show dialog
                dialog.show();

                //Initialize and assign variable
                EditText editSystolicText = dialog.findViewById(R.id.bp_systolic_text_view);
                EditText editDiastolicText = dialog.findViewById(R.id.bp_diastolic_text_view);
                EditText editNotesText = dialog.findViewById(R.id.bp_notes_text_view);
                Button bpUpdate = dialog.findViewById(R.id.bp_update);

                //Schema is decision programmed into the DAO

                //Hash map to lookup table
                //Know lower bound and upper bound

                //Set text on edit text
                editSystolicText.setText(sText);
                //Retrieve it right away

                editDiastolicText.setText(dText);

                editNotesText.setText(nText);

                bpUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editSystolicText.getText().toString().trim();
                        String vText = editDiastolicText.getText().toString().trim();
                        String wText = editNotesText.getText().toString().trim();
                        //Update text in database
                        bpDatabase.bpDao().updateSystolic(sID,uText);
                        bpDatabase.bpDao().updateDiastolic(sID,vText);
                        bpDatabase.bpDao().updateNotes(sID, wText);
                        //Maybe fix something here to update them all
                        //Notify when data is updated
                        bpDataList.clear();
                        bpDataList.addAll(bpDatabase.bpDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });*/

        /*
        holder.bpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                bpData d = bpDataList.get(holder.getAdapterPosition());
                //Delete text from database
                bpDatabase.bpDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                bpDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, bpDataList.size());
            }
        });

         */
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
        //ImageView bpEdit;
        //ImageView bpDelete;

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
            //bpEdit = itemView.findViewById(R.id.bp_edit);
            //bpDelete = itemView.findViewById(R.id.bp_delete);
        }
    }
}