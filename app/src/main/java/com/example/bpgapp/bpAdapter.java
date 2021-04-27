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
        holder.textView.setText(data.getBpText());

        holder.bpEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("bp Edit on click method is here");
                //Initialize main data
                bpData d = bpDataList.get(holder.getAdapterPosition());
                //Get ID
                int sID = d.getID();
                //Get text
                String sText = d.getBpText();

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

                //Schema is decision programmed into the DAO

                //Set text on edit text
                editText.setText(sText);
                //Retrieve it right away

                //System.out.println("Systolic value stored: " + sText);
                Log.d("talia", "Systolic value stored in bpAdapter: " + sText);

                bpUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        bpDatabase.bpDao().update(sID,uText);
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
        TextView textView;
        ImageView bpEdit, bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            textView = itemView.findViewById(R.id.bp_text_view);
            bpEdit = itemView.findViewById(R.id.bp_edit);
            bpDelete = itemView.findViewById(R.id.bp_delete);
        }
    }
}
