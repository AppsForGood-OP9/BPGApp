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

    //Initialize variable
    private List<GData> gDataList;
    private Activity context;
    private GRoomDB gDatabase;

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
        //Initialize main data
        GData data = gDataList.get(position);
        //Initialize database
        gDatabase = GRoomDB.getInstance(context);
        //Set text on text view
        //This is where the error was
        holder.gTimeTextView.setText(data.getTime());
        holder.gDateTextView.setText(data.getDate());
        holder.gGlucoseTextView.setText(data.getGlucoseText());
        holder.gNotesTextView.setText(data.getNotesText());
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
        });
        */

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

    @Override
    public int getItemCount() {
        return gDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView gDateTextView;
        TextView gTimeTextView;
        TextView gGlucoseTextView;
        TextView gNotesTextView;
        //ImageView bpEdit;
        //ImageView bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            gDateTextView = itemView.findViewById(R.id.g_date_text_view);
            gTimeTextView = itemView.findViewById(R.id.g_time_text_view);
            gGlucoseTextView = itemView.findViewById(R.id.g_glucose_text_view);
            gNotesTextView = itemView.findViewById(R.id.g_notes_text_view);
            // bpEdit = itemView.findViewById(R.id.bp_edit);
            // bpDelete = itemView.findViewById(R.id.bp_delete);
        }
    }
}
