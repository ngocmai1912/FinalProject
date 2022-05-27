package com.example.finalproject.ui;

import static com.example.finalproject.MainActivity.mainCart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.example.finalproject.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment implements CartProductItemAdapter.OnItemListener {
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
                if(mainCart.getTotalProduct() != 0){
                    Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkoutFragment);
                }
                else{
                    AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                            .setMessage("Bạn chưa có sản phẩm nào trong giỏ!")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
        binding.txtTotalProduct.setText(mainCart.getTotalProduct()+"");
        binding.txtTotalPrice.setText(mainCart.getTotalPrice()+"");
        adapter = new CartProductItemAdapter(getContext(), this, mainCart.getCartProductList(), false);
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

    @Override
    public void onClick() {
        Log.d("TAGLOG", "gelele");
        binding.txtTotalPrice.setText(mainCart.getTotalPrice()+"");
        binding.txtTotalProduct.setText(mainCart.getTotalProduct()+"");
    }

}
