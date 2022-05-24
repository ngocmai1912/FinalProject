package com.example.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private int id;
    private float totalPrice;
    private int totalProduct;
    private List<CartProduct> cartProductList;

    public Cart() {
        cartProductList = new ArrayList<>();
    }

    public Cart(int id, float totalPrice, int totalProduct, List<CartProduct> cartProductList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalProduct = totalProduct;
        this.cartProductList = cartProductList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", totalProduct=" + totalProduct +
                ", CartProduct=" + cartProductList +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalPrice() {
        float total = 0;
        for(CartProduct cd : cartProductList){
            total += cd.getAmount()*cd.getProduct().getPrice();
        }
        return total;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalProduct() {
        int total = 0;
        for(CartProduct cd : cartProductList){
            total += cd.getAmount();
        }
        return total;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public List<com.example.finalproject.model.CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<com.example.finalproject.model.CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }
}
