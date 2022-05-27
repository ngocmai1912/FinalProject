package com.example.finalproject.adapter;

import static com.example.finalproject.MainActivity.mainCart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.CartProduct;
import com.example.finalproject.model.Product;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CartProductItemAdapter extends RecyclerView.Adapter<CartProductItemAdapter.ItemViewHolder> {
    Context context;
    List<CartProduct> listProduct;
    OnItemListener listener;
    Boolean checkInCheckOut;
    public CartProductItemAdapter(Context context, OnItemListener listener, List<CartProduct> listProduct, Boolean checkInCheckOut) {
        this.context = context;
        this.listProduct = listProduct;
        this.checkInCheckOut = checkInCheckOut;
        this.listener = listener;
    }
    public CartProductItemAdapter(Context context, List<CartProduct> listProduct, Boolean checkInCheckOut) {
        this.context = context;
        this.listProduct = listProduct;
        this.checkInCheckOut = checkInCheckOut;
    }
    public CartProductItemAdapter(Context context){
        listProduct = new ArrayList<>();
        this.context = context;
    }
    public void setListProduct(List<CartProduct> list){
        this.listProduct = list;
    }
    public void addItem(CartProduct product){
        listProduct.add(product);
        notifyDataSetChanged();
    }
//    public void setClickListener(OnItemListener listener){
//        this.listener = listener;
//    }
    @NonNull
    @Override
    public CartProductItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_cart, parent, false);
        return new CartProductItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductItemAdapter.ItemViewHolder holder, int position) {
        CartProduct product = listProduct.get(position);
        if(checkInCheckOut){
            holder.btnIncrease.setVisibility(View.GONE);
            holder.btnDecrease.setVisibility(View.GONE);
        }
        holder.txtName.setText(product.getProduct().getName());
        holder.txtPrice.setText(product.getProduct().getPrice()*product.getAmount()+" đ");
        holder.txtAmount.setText(product.getAmount()+"");
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = mainCart.getCartProductList().indexOf(product);
                int amount = Integer.parseInt(holder.txtAmount.getText().toString());
                amount++;
                mainCart.getCartProductList().get(p).setAmount(amount);
                holder.txtAmount.setText(amount+"");
                product.setAmount(amount);
                holder.txtPrice.setText(product.getProduct().getPrice()*product.getAmount()+" đ");
                listener.onClick();
            }
        });
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = mainCart.getCartProductList().indexOf(product);
                int amount = Integer.parseInt(holder.txtAmount.getText().toString());
                if(amount > 1){
                    amount--;
                    mainCart.getCartProductList().get(p).setAmount(amount);
                    holder.txtAmount.setText(amount+"");
                    product.setAmount(amount);
                    holder.txtPrice.setText(product.getProduct().getPrice()*product.getAmount()+" đ");
                    listener.onClick();
                }
                else{
                    AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Xóa sản phẩm khỏi giỏ hàng?")
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mainCart.getCartProductList().remove(product);
                                    listProduct.remove(product);
                                    notifyDataSetChanged();
                                    listener.onClick();
                                }

                            })
                            .setNegativeButton("Hủy bỏ", null)
                            .show();
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF7B67"));
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7B67"));
                }
            }
        });
        Glide.with(context).load(product.getProduct().getImage()).centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView image;
        TextView txtName, txtPrice, txtAmount;
        AppCompatImageButton btnIncrease, btnDecrease;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_product_name);
            txtPrice = itemView.findViewById(R.id.txt_product_price);
            image = itemView.findViewById(R.id.product_image);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            txtAmount  = itemView.findViewById(R.id.txt_amount);
        }
    }
    public CartProduct getItem(int position){
        return listProduct.get(position);
    }
    public interface OnItemListener{
        public void onClick();
    }
}
