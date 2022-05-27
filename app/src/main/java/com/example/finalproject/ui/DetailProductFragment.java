package com.example.finalproject.ui;

import static com.example.finalproject.MainActivity.mainCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentDetailProductBinding;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.CartProduct;
import com.example.finalproject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DetailProductFragment extends Fragment {
    FragmentDetailProductBinding binding;
    Product product;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        product = (Product) getArguments().getSerializable("product");
        if(product != null){
            binding.txtProductName.setText(product.getName());
            binding.txtProductPrice.setText(product.getPrice()+"");
            binding.txtProductDescription.setText(product.getDes());
            Glide.with(getContext()).load(product.getImage()).centerCrop().into(binding.imgProduct);
        }
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_detailProductFragment_to_cartFragment);
            }
        });
        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(binding.txtAmount.getText().toString());
                amount++;
                binding.txtAmount.setText(amount+"");
            }
        });
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(binding.txtAmount.getText().toString());
                if(amount > 1) amount--;
                binding.txtAmount.setText(amount+"");
            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(binding.txtAmount.getText().toString());
                Boolean check = false;
                List<CartProduct> list = mainCart.getCartProductList();
                for(CartProduct item : list){
                    if(item.getProduct().getId() == product.getId()){
                        item.setAmount(item.getAmount()+amount);
                        check = true;
                        break;
                    }
                }
                if(!check){
                    CartProduct cartProduct = new CartProduct(product, amount);
                    list.add(cartProduct);
                }
                mainCart.setCartProductList(list);
                Toast.makeText(getContext(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                List<CartProduct> list = new ArrayList<>();
                int amount = Integer.parseInt(binding.txtAmount.getText().toString());
                list.add(new CartProduct(product, amount));
                mainCart = new Cart(product.getPrice()*amount, amount, list);
                Navigation.findNavController(view).navigate(R.id.action_detailProductFragment_to_checkoutFragment);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.show();
    }
}
