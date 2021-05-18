package com.example.bpgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The TableAdapterG handles the adapter for the glucose table.
 */
public class TableAdapterG extends RecyclerView.Adapter<TableAdapterG.ViewHolder> {
    //Initialize variables
    Context context;
    private List<TableModelG> table_list;
    private List<GData> dataList;
    private GRoomDB gDatabase;

    /**
     * Constructs a TableAdapterG object
     * @param context the context
     * @param table_list the TableModel data list
     * @param dataList the GData data list
     */
    public TableAdapterG(Context context, List<TableModelG> table_list, List<GData> dataList)  {
        this.context = context;
        this.table_list = table_list;
        this.dataList = dataList;
        //notifyDataSetChanged();
        //Try to uncomment these?
    }

    @NonNull
    //@Override
    public TableAdapterG.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_g, parent, false);
        return new TableAdapterG.ViewHolder(view);
    }

    //@Override
    public void onBindViewHolder(@NonNull TableAdapterG.ViewHolder holder, int position) {
        GData data = dataList.get(position);

        //Initialize database
        gDatabase = GRoomDB.getInstance(context);

        //Set the text for each item in the table
        if(table_list != null && table_list.size() > 0)  {
            TableModelG model = table_list.get(position);
            holder.dateItem.setText(model.getDate());
            holder.timeItem.setText(model.getTime());
            holder.glucoseItem.setText(model.getGlucose());
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
    public int getItemCount() {
        return table_list.size();
    }

    /**
     * Constructs the corresponding ViewHolder for the RecyclerView.ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateItem,timeItem,glucoseItem,notesItem;
        //ImageView bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateItem = itemView.findViewById(R.id.dateItem);
            timeItem = itemView.findViewById(R.id.timeItem);
            glucoseItem = itemView.findViewById(R.id.glucoseItem);
            notesItem = itemView.findViewById(R.id.notesItem);
        }

    }
}
