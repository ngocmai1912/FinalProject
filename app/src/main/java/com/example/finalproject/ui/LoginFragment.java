package com.example.finalproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    Boolean checkHidePass;
    FirebaseAuth fbAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkHidePass = true;
        binding.edtPass.setTransformationMethod(new PasswordTransformationMethod());

        fbAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fbAuth.getCurrentUser();
        if(user != null){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        }
        else{
            binding.register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
                }
            });
            binding.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = binding.edtEmail.getText().toString();
                    String pass = binding.edtPass.getText().toString();
                    if(email.isEmpty()){
                        binding.edtEmail.setError("Không được để trống");
                        binding.edtEmail.requestFocus();
                        return;
                    } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        binding.edtEmail.setError("Sai định dạng email");
                        binding.edtEmail.requestFocus();
                        return;
                    }
                    if(pass.isEmpty()){
                        binding.edtPass.setError("Không được để trống");
                        binding.edtPass.requestFocus();
                        return;
                    }
                    fbAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Sai thông tin đăng nhập!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            binding.btnToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkHidePass) {
                        binding.edtPass.setTransformationMethod(null);
                        binding.btnToggle.setImageResource(R.drawable.ic_hide);
                        checkHidePass = false;
                    }
                    else{
                        binding.edtPass.setTransformationMethod(new PasswordTransformationMethod());
                        binding.btnToggle.setImageResource(R.drawable.ic_see);
                        checkHidePass = true;
                    }
                }
            });
        }


    }
}