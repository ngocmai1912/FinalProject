package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.model.Account;
import com.example.finalproject.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    NavHostFragment navHostFragment;
    FirebaseAuth fbAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fbAuth = FirebaseAuth.getInstance();
        navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        //get data
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        Product p1 = new Product("P1",
//                        "Tai nghe",
//                        "Ask to be pet then attack owners hand cats are cute.",
//                        100,
//                        "https://i.pinimg.com/564x/66/2c/e4/662ce4f20647d212ae0fa879843a157c.jpg"
//
//        );
//        Product p2 = new Product("P2",
//                        "Tai nghe không dây",
//                        "Ask to be pet then attack owners hand cats are cute.",
//                        100,
//                        "https://i.pinimg.com/564x/66/2c/e4/662ce4f20647d212ae0fa879843a157c.jpg"
//
//        );
//        Product p3 = new Product("P3",
//                        "Gương",
//                        "Ask to be pet then attack owners hand cats are cute.",
//                        104,
//                        "https://secure.img1-fg.wfcdn.com/im/26840454/resize-h445%5Ecompr-r85/1942/194268820/Double+Sided+Pedestal+Magnification+And+True+Image+Makeup+Mirror.jpg"
//                );
//        Product p4 = new Product("P4",
//                        "Gương",
//                        "Ask to be pet then attack owners hand cats are cute.",
//                        104,
//                        "https://i.pinimg.com/236x/8b/22/8a/8b228aa0ede1b56339e89cc0aaaafbea.jpg"
//        );
//
//        mDatabase.child("product").child(p1.getId()+"").setValue(p1);
//        mDatabase.child("product").child(p2.getId()+"").setValue(p2);
//        mDatabase.child("product").child(p3.getId()+"").setValue(p3);
//        mDatabase.child("product").child(p4.getId()+"").setValue(p4);
    }
}