package com.example.finalproject.ui;

import static com.example.finalproject.MainActivity.mainCart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.adapter.CartProductItemAdapter;
import com.example.finalproject.databinding.FragmentCheckoutBinding;
import com.example.finalproject.model.Account;
import com.example.finalproject.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CheckoutFragment extends Fragment {
    FragmentCheckoutBinding binding;
    CartProductItemAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
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
        float totalPrice = mainCart.getTotalPrice() + 25;
        binding.txtTotalPriceProduct.setText(mainCart.getTotalPrice()+" đ");
        binding.txtTotalPrice.setText(totalPrice+" đ");
        adapter = new CartProductItemAdapter(getContext(), mainCart.getCartProductList(), true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.listItem.setAdapter(adapter);
        binding.listItem.setLayoutManager(manager);
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = binding.edtAddress.getText().toString();
                String phone = binding.edtPhone.getText().toString();
                if(address.isEmpty()){
                    binding.edtAddress.setError("Không được để trống");
                    binding.edtAddress.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    binding.edtPhone.setError("Không được để trống");
                    binding.edtPhone.requestFocus();
                    return;
                }
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Account account = new Account(currentUser.getUid(), currentUser.getEmail());
                String status = "Chờ xác nhận";


                AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                        .setMessage("Xác nhận đặt hàng?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //ghi du lieu len firebase
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                String orderId = mDatabase.push().getKey();
                                Calendar calendar = Calendar.getInstance();
                                String date = calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
                                Order order = new Order(orderId, mainCart, account, address, phone, totalPrice, status, date);
                                mDatabase.child("orders").child(orderId).setValue(order);
                               returnHome(view);
                            }

                        })
                        .setNegativeButton("Hủy bỏ", null)
                        .show();
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF7B67"));
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7B67"));
            }
        });


    }

    public void returnHome(View view){
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                .setMessage("Đặt hàng thành công!")
                .setPositiveButton("Quay về trang chủ", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(view).navigate(R.id.action_checkoutFragment_to_homeFragment);
                    }

                })
                .show();
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