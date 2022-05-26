package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalproject.model.Cart;
import com.example.finalproject.model.Product;
import com.example.finalproject.ui.ChangePassDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavHostFragment navHostFragment;
    FirebaseAuth fbAuth;
    DatabaseReference mDatabase;
    public static Cart mainCart;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fbAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fbAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        mainCart = new Cart();
        navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get data
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        Product p4 = new Product("P5",
                        "Váy hoa nhí",
                        "Ask to be pet then attack owners hand cats are cute.",
                        104000,
                        "https://i.pinimg.com/236x/8b/22/8a/8b228aa0ede1b56339e89cc0aaaafbea.jpg"
        );
//
//        mDatabase.child("product").child(p1.getId()+"").setValue(p1);
//        mDatabase.child("product").child(p2.getId()+"").setValue(p2);
//        mDatabase.child("product").child(p3.getId()+"").setValue(p3);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_change_pass){
            ChangePassDialog dialog = new ChangePassDialog();
            dialog.show(getSupportFragmentManager(), "ChangePass");
        } else if(item.getItemId() == R.id.nav_listOrder){
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

}