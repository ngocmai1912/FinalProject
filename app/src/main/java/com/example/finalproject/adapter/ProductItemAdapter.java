package com.example.finalproject.adapter;

import static com.example.finalproject.MainActivity.mainCart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.CartProduct;
import com.example.finalproject.model.Product;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ItemViewHolder> {
    Context context;
    List<Product> listProduct;
    OnItemListener listener;
    public ProductItemAdapter(Context context, List<Product> listProduct) {
            this.context = context;
            this.listProduct = listProduct;
    }

    public ProductItemAdapter(Context context){
        listProduct = new ArrayList<>();
        this.context = context;
    }
    public void setListProduct(List<Product> list){
        this.listProduct = list;
    }
    public void clear(){
        listProduct.clear();
        notifyDataSetChanged();
    }
    public void addItem(Product product){
        listProduct.add(product);
        notifyDataSetChanged();
    }
    public void setClickListener(OnItemListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice()+" đ");
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ShapeableImageView image;
        TextView txtName, txtPrice;
        AppCompatButton btnBuy;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_product_name);
            txtPrice = itemView.findViewById(R.id.txt_product_price);
            image = itemView.findViewById(R.id.product_image);
            btnBuy = itemView.findViewById(R.id.btn_buy_now);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(itemView, getAdapterPosition());
                }
            });
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonClick(itemView, getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public Product getItem(int position){
        return listProduct.get(position);
    }
    public interface OnItemListener{
        public void onClick(View view, int position);
        public void onButtonClick(View view, int position);
    }
}
