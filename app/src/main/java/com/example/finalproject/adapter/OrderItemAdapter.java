package com.example.finalproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Order;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder> {
    Context context;
    List<Order> listOrder;
    OnItemListener listener;
    public OrderItemAdapter(Context context, List<Order> listOrder) {
            this.context = context;
            this.listOrder = listOrder;
    }

    public OrderItemAdapter(Context context){
        listOrder = new ArrayList<>();
        this.context = context;
    }
    public void setListOrder(List<Order> list){
        this.listOrder = list;
    }
    public void addItem(Order Order){
        Log.d("TAGLOG", "add adapter");
        listOrder.add(Order);
        notifyDataSetChanged();
    }
    public void setClickListener(OnItemListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Order order = listOrder.get(position);
        holder.txtID.setText(order.getId());
        holder.txtDate.setText(order.getDate());
        holder.txtStatus.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtID, txtDate, txtStatus;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.order_id);
            txtDate = itemView.findViewById(R.id.order_date);
            txtStatus = itemView.findViewById(R.id.order_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public Order getItem(int position){
        return listOrder.get(position);
    }
    public interface OnItemListener{
        public void onClick(View view, int position);
    }
}
