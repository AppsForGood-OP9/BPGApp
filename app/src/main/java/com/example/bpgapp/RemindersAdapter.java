package com.example.bpgapp;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder>{

    //Initialize variable
    private List<RemindersData> remindersDataList;
    private Activity context;
    private RemindersRoomDB remindersDatabase;

    //Create constructor
    public RemindersAdapter(Activity context, List<RemindersData> dataList)  {
        this.context = context;
        this.remindersDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminders_list_row_main, parent, false);
        return new RemindersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindersAdapter.ViewHolder holder, int position) {
        //Initialize main data
        RemindersData data = remindersDataList.get(position);
        //Initialize database
        remindersDatabase = RemindersRoomDB.getInstance(context);
        //Set text on text view
        holder.remindersTimeTextView.setText(data.getTime());
        holder.remindersDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                RemindersData d = remindersDataList.get(holder.getAdapterPosition());
                //Delete text from database
                remindersDatabase.RemindersDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                remindersDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, remindersDataList.size());
            }
        });

        holder.remindersEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                RemindersData d = remindersDataList.get(holder.getAdapterPosition());
                //Get ID
                int timeID = d.getID();
                //Get text
                String timeText = d.getTime();

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
                EditText editText = dialog.findViewById(R.id.bp_edit_text);
                if (dialog.findViewById(R.id.bp_edit_text) == null){
                }
                Button bpUpdate = dialog.findViewById(R.id.bp_update);

                //Schema is decision programmed into the DAO
                if (editText == null){
                }
                if (timeText == null){
                }
                //Set text on edit text
                editText.setText(timeText);
                //Retrieve it right away

                //System.out.println("Systolic value stored: " + sText);

                bpUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        remindersDatabase.RemindersDao().update(timeID, uText);
                        //Notify when data is updated
                        remindersDataList.clear();
                        remindersDataList.addAll(remindersDatabase.RemindersDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }
        public int getItemCount() {
        return remindersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Initialize variables
        private TextView remindersTimeTextView;
        private ImageView remindersEdit;
        private ImageView remindersDelete;
        //Initialize Variables
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            remindersTimeTextView = itemView.findViewById(R.id.reminders_time_text_view);
            remindersEdit = itemView.findViewById(R.id.reminders_edit);
            remindersDelete = itemView.findViewById(R.id.reminders_delete);
        }
    }
    }

