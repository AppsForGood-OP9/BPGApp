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

    /**
     * Class constructor
     * @param context
     * @param dataList data from the reminder data
     */
    public RemindersAdapter(Activity context, List<RemindersData> dataList)  {
        this.context = context;
        this.RemindersDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * Overriding the parent onCreate method to bring up the reminder list row main layout
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminders_list_row_main, parent, false);
        return new RemindersAdapter.ViewHolder(view);
    }

    /**
     * Overriding the parent onBindViewHolder to initialize and display the data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RemindersAdapter.ViewHolder holder, int position) {
        //Initialize main data
        RemindersData data = RemindersDataList.get(position);
        //Initialize database

        RemindersDatabase = RemindersRoomDB.getInstance(context);
        //Set text on text view
        holder.remindersTimeTextView.setText(data.getTime());
        holder.remindersDelete.setOnClickListener(new View.OnClickListener() {

            /**
             * On click method that will change the dao and notify that the item was removed
             * @param v
             */
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

        /**
         * If the edit button is pressed bring up a pop up where they can change the value
         */
        holder.remindersEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Button bpUpdate = dialog.findViewById(R.id.bp_update);

                //Set text on edit text
                editText.setText(timeText);
                //Retrieve it right away

                /**
                 * When the update button from the dialog is pressed save the new value
                 * return to the original view
                 */
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

    /**
     * Returns the size of the datalist
     * @return the number of items in the datalist
     */
    public int getItemCount() {
        return RemindersDataList.size();
    }

    /**
     * Viewholder class to handle the variables
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        //Initialize variables
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

