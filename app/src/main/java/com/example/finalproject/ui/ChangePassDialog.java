package com.example.finalproject.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.databinding.DialogChangePassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassDialog extends DialogFragment{
    DialogChangePassBinding binding;
    private boolean checkHidePassword, checkHideCfPassword, checkNewPass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogChangePassBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkHidePassword = true;
        checkHideCfPassword = true;
        checkNewPass = true;
        binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        binding.edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
        binding.edtNewPassword.setTransformationMethod(new PasswordTransformationMethod());

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fbAuth.getCurrentUser();
        binding.btnTogglePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkHidePassword){
                    binding.edtPassword.setTransformationMethod(null);
                    binding.btnTogglePass.setImageResource(R.drawable.ic_hide);
                    checkHidePassword = false;
                }
                else{
                    binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.btnTogglePass.setImageResource(R.drawable.ic_see);
                    checkHidePassword = true;
                }
            }
        });
        binding.btnToggleConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkHideCfPassword){
                    binding.edtConfirmPassword.setTransformationMethod(null);
                    binding.btnToggleConfirmPass.setImageResource(R.drawable.ic_hide);
                    checkHideCfPassword = false;
                }
                else{
                    binding.edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.btnToggleConfirmPass.setImageResource(R.drawable.ic_see);
                    checkHideCfPassword = true;
                }
            }
        });
        binding.btnToggleNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNewPass){
                    binding.edtNewPassword.setTransformationMethod(null);
                    binding.btnToggleNewPass.setImageResource(R.drawable.ic_hide);
                    checkNewPass = false;
                }
                else{
                    binding.edtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.btnToggleNewPass.setImageResource(R.drawable.ic_see);
                    checkNewPass = true;
                }
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = binding.edtPassword.getText().toString();
                String newPass = binding.edtNewPassword.getText().toString();
                String cfPass = binding.edtConfirmPassword.getText().toString();
                if(pass.isEmpty()){
                    binding.edtPassword.setError("Không được để trống");
                    binding.edtPassword.requestFocus();
                    return;
                }
                if(newPass.isEmpty()){
                    binding.edtNewPassword.setError("Không được để trống");
                    binding.edtNewPassword.requestFocus();
                    return;
                }
                if(cfPass.isEmpty()){
                    binding.edtConfirmPassword.setError("Không được để trống");
                    binding.edtConfirmPassword.requestFocus();
                    return;
                }
                if(newPass.compareTo(cfPass) != 0){
                    binding.edtConfirmPassword.setError("Mật khẩu không khớp");
                    binding.edtConfirmPassword.requestFocus();
                    return;
                }
                fbAuth.signInWithEmailAndPassword(currentUser.getEmail(),pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAGLOG", "changePass:success");
                                    Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                } else {
                                    Log.d("TAGLOG", "changePass:failure", task.getException());
                                    Toast.makeText(getContext(), "Đổi mật khẩu không thành công. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        binding.edtPassword.setError("Sai mật khẩu");
                        binding.edtPassword.requestFocus();
                        return;
                    }
                });

            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = 1350;
        int height = 1700;
        Window window = getDialog().getWindow();
        window.setLayout(width, height);
    }
}
