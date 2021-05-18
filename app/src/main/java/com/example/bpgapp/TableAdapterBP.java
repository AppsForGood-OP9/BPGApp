package com.example.bpgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The TableAdapterG handles the adapter for the glucose table.
 */
public class TableAdapterBP extends RecyclerView.Adapter<TableAdapterBP.ViewHolder>{
    //Initialize variables
    Context context;
    private List<TableModelBP> table_list;
    private List<BPData> dataList;
    private BPRoomDB bpDatabase;

    /**
     * Constructs a TableAdapterBP object
     * @param context the context
     * @param table_list the TableModel data list
     * @param dataList the BPData data list
     */
    public TableAdapterBP(Context context, List<TableModelBP> table_list, List<BPData> dataList)  {
        this.context = context;
        this.table_list = table_list;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BPData data = dataList.get(position);
        //Initialize database
        bpDatabase = BPRoomDB.getInstance(context);

        //Set the text for each item in the table
        if(table_list != null && table_list.size() > 0)  {
            TableModelBP model = table_list.get(position);
            holder.dateItem.setText(model.getDate());
            holder.timeItem.setText(model.getTime());
            holder.systolicItem.setText(model.getSystolic());
            holder.diastolicItem.setText(model.getDiastolic());
            holder.notesItem.setText(model.getNotes());
        }
        else  {
            return;
        }

    }

    /**
     * Returns the number of items in the table data list
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return table_list.size();
    }

    /**
     * Constructs the corresponding ViewHolder for the RecyclerView.ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateItem,timeItem,systolicItem,diastolicItem,notesItem;
        //ImageView bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateItem = itemView.findViewById(R.id.dateItem);
            timeItem = itemView.findViewById(R.id.timeItem);
            systolicItem = itemView.findViewById(R.id.systolicItem);
            diastolicItem = itemView.findViewById(R.id.diastolicItem);
            notesItem = itemView.findViewById(R.id.notesItem);
        }

    }
}
