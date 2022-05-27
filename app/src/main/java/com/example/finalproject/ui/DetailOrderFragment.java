package com.example.finalproject.ui;

import static com.example.finalproject.MainActivity.mainCart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.adapter.CartProductItemAdapter;
import com.example.finalproject.databinding.FragmentDetailOrderBinding;
import com.example.finalproject.model.Order;
import com.example.finalproject.model.Product;

public class DetailOrderFragment extends Fragment {
    FragmentDetailOrderBinding binding;
    CartProductItemAdapter adapter;
    Order order;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = (Order) getArguments().getSerializable("order");

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        adapter = new CartProductItemAdapter(getContext(), order.getCart().getCartProductList(), true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.listItem.setAdapter(adapter);
        binding.listItem.setLayoutManager(manager);
        binding.txtStatus.setText("Trạng thái: " + order.getStatus());
        binding.txtDate.setText("Ngày đặt: " + order.getDate());
        binding.txtAddress.setText(order.getAddress());
        binding.txtPhone.setText(order.getPhoneNumber());
        binding.txtTotalPriceProduct.setText((order.getTotalPrice()-25) + "");
        binding.txtTotalPrice.setText(order.getTotalPrice()+"");
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