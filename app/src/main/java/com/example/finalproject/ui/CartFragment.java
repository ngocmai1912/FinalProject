package com.example.finalproject.ui;

import static com.example.finalproject.MainActivity.mainCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalproject.R;
import com.example.finalproject.adapter.CartProductItemAdapter;
import com.example.finalproject.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    CartProductItemAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkoutFragment);
            }
        });
        binding.txtTotalProduct.setText(mainCart.getTotalProduct()+"");
        binding.txtTotalPrice.setText(mainCart.getTotalPrice()+"");
        adapter = new CartProductItemAdapter(getContext(), mainCart.getCartProductList());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.listItem.setAdapter(adapter);
        binding.listItem.setLayoutManager(manager);

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
