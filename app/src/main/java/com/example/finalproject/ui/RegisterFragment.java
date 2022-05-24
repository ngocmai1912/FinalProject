package com.example.finalproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    Boolean checkHidePass, checkHideCfPass;
    FirebaseAuth fbAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkHidePass = true;
        checkHideCfPass = true;
        binding.edtPass.setTransformationMethod(new PasswordTransformationMethod());
        binding.edtCfpass.setTransformationMethod(new PasswordTransformationMethod());

        fbAuth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                String pass = binding.edtPass.getText().toString();
                String cfPass = binding.edtCfpass.getText().toString();
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
                if (cfPass.isEmpty()){
                    binding.edtCfpass.setError("Không được để trống");
                    binding.edtCfpass.requestFocus();
                    return;
                }
                if(pass.compareTo(cfPass) != 0){
                    binding.edtCfpass.setError("Mật khẩu không khớp");
                    binding.edtCfpass.requestFocus();
                    return;
                }
                fbAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAGLOG", "createUserWithEmail:success");
                                    Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                                } else {
                                    Log.d("TAGLOG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Đăng ký không thành công. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
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
        binding.btnCftoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkHideCfPass) {
                    binding.edtCfpass.setTransformationMethod(null);
                    binding.btnCftoggle.setImageResource(R.drawable.ic_hide);
                    checkHideCfPass = false;
                }
                else{
                    binding.edtCfpass.setTransformationMethod(new PasswordTransformationMethod());
                    binding.btnCftoggle.setImageResource(R.drawable.ic_see);
                    checkHideCfPass = true;
                }
            }
        });
    }
}