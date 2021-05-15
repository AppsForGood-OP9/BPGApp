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

public class TableAdapterG extends RecyclerView.Adapter<TableAdapterG.ViewHolder> {
    Context context;
    private List<TableModelG> table_list;
    private List<GData> dataList;
    private GRoomDB gDatabase;

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

        holder.bpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                GData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                gDatabase.gDao().delete(d);
                gDatabase.gDao().deleteAll();
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                //notifyItemRemoved(position);
                //notifyItemRangeChanged(position, dataList.size());
            }
        });

    }

    //@Override
    public int getItemCount() {
        return table_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateItem,timeItem,glucoseItem,notesItem;
        ImageView bpDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateItem = itemView.findViewById(R.id.dateItem);
            timeItem = itemView.findViewById(R.id.timeItem);
            glucoseItem = itemView.findViewById(R.id.glucoseItem);
            notesItem = itemView.findViewById(R.id.notesItem);
            bpDelete = itemView.findViewById(R.id.bp_delete);
        }

    }
}
