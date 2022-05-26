package com.example.finalproject.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.adapter.ProductItemAdapter;
import com.example.finalproject.databinding.FragmentHomeBinding;
import com.example.finalproject.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductItemAdapter.OnItemListener {
    FragmentHomeBinding binding;
    RecyclerView rcv;
    ProductItemAdapter adapter;
    List<Product> listProduct;
    FirebaseDatabase mDatabase;
//    DrawerLayout drawerLayout;
//    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_list_item);
        mDatabase = FirebaseDatabase.getInstance();
        List<Product> productList = new ArrayList<>();
        adapter = new ProductItemAdapter(getContext());
        mDatabase.getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                productList.clear();
                listProduct = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    adapter.addItem(product);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " );
            }
        });
        adapter.setClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view, int position) {
        Product product = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailProductFragment, bundle);
    }

    public void search(String textSearch){
        mDatabase.getReference().child("product")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listProduct = new ArrayList<>();
                adapter.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if(product.getName().toLowerCase().contains(textSearch.toLowerCase())){
                        adapter.addItem(product);
                        Log.d("TAGLOG 1", product.toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " );
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);

        //search
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        //cart
        MenuItem btnCart = menu.findItem(R.id.action_cart);
        btnCart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_cartFragment);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}