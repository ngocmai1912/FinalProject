package com.example.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentDetailProductBinding;
import com.example.finalproject.model.Product;

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
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
}
