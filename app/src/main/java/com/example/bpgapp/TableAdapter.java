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

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{

    Context context;
    private List<TableModel> table_list;
    private List<bpData> dataList;
    private bpRoomDB bpDatabase;

    public TableAdapter(Context context, List<TableModel> table_list, List<bpData> dataList)  {
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
        bpData data = dataList.get(position);
        //Initialize database
        bpDatabase = bpRoomDB.getInstance(context);

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

        /*
        holder.bpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                bpData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                bpDatabase.bpDao().delete(d);
                bpDatabase.bpDao().deleteAll();
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return table_list.size();
    }

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
            //bpDelete = itemView.findViewById(R.id.bp_delete);
        }

    }
}
