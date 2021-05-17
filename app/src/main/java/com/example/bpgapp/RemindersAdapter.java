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
    private List<RemindersData> RemindersDataList;
    private Activity context;
    private RemindersRoomDB RemindersDatabase;

    //Create constructor
    public RemindersAdapter(Activity context, List<RemindersData> dataList)  {
        this.context = context;
        this.RemindersDataList = dataList;
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
        RemindersData data = RemindersDataList.get(position);
        //Initialize database
        RemindersDatabase = RemindersRoomDB.getInstance(context);
        //Set text on text view
        holder.remindersTimeTextView.setText(data.getTime());
        holder.remindersDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                RemindersData d = RemindersDataList.get(holder.getAdapterPosition());
                //Delete text from database
                RemindersDatabase.RemindersDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                RemindersDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, RemindersDataList.size());
            }
        });

        holder.remindersEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Yiming","bp Edit on click method is here");
                //Initialize main data
                RemindersData d = RemindersDataList.get(holder.getAdapterPosition());
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
                    Log.d("Yiming", "reminders_edit is null");
                }
                Button bpUpdate = dialog.findViewById(R.id.bp_update);

                //Schema is decision programmed into the DAO
                if (editText == null){
                    Log.d("Yiming", "editText is null");
                }
                if (timeText == null){
                    Log.d("Yiming", "timeText is null");
                }
                //Set text on edit text
                editText.setText(timeText);
                //Retrieve it right away

                //System.out.println("Systolic value stored: " + sText);
                Log.d("talia", "Time value stored in RemindersAdapter: " + timeText);

                bpUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        RemindersDatabase.RemindersDao().update(timeID, uText);
                        //Notify when data is updated
                        RemindersDataList.clear();
                        RemindersDataList.addAll(RemindersDatabase.RemindersDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }
        public int getItemCount() {
        return RemindersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{//Initialize variable
        TextView remindersTimeTextView;
        ImageView remindersEdit;
        ImageView remindersDelete;
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

