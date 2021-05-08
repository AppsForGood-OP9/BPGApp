package com.example.bpgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{

    Context context;
    List<TableModel> table_list;

    public TableAdapter(Context context, List<TableModel> table_list)  {
        this.context = context;
        this.table_list = table_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(table_list != null && table_list.size() > 0)  {
            TableModel model = table_list.get(position);
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

    @Override
    public int getItemCount() {
        return table_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateItem,timeItem,systolicItem,diastolicItem,notesItem;
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
