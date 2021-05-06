package com.example.bpgapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class bpAdapter extends RecyclerView.Adapter<bpAdapter.ViewHolder> {

    //Initialize variable
    private List<bpData> bpDataList;
    private Activity context;
    private bpRoomDB bpDatabase;

    //Create constructor
    public bpAdapter(Activity context, List<bpData> dataList)  {
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
//I changed something here, check if bpAdapter.ViewHolder or not
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize main data
        bpData data = bpDataList.get(position);
        //Initialize database
        bpDatabase = bpRoomDB.getInstance(context);
        //Set text on text view
        //This is where the error was
        holder.bpSystolicTextView.setText(data.getSystolicText());
        holder.bpDiastolicTextView.setText(data.getDiastolicText());
        holder.bpNotesTextView.setText(data.getNotesText());

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
                        //Notify when data is updated
                        bpDataList.clear();
                        bpDataList.addAll(bpDatabase.bpDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return bpDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView bpSystolicTextView;
        TextView bpDiastolicTextView;
        TextView bpNotesTextView;
        ImageView bpEdit, bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            bpSystolicTextView = itemView.findViewById(R.id.bp_systolic_text_view);
            bpDiastolicTextView = itemView.findViewById(R.id.bp_diastolic_text_view);
            bpNotesTextView = itemView.findViewById(R.id.bp_notes_text_view);
            bpEdit = itemView.findViewById(R.id.bp_edit);
            bpDelete = itemView.findViewById(R.id.bp_delete);
        }
    }
}

//Make a data query
//Will give you back a list object
//In the screen where you want the retrieval