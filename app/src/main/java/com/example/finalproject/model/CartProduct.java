package com.example.finalproject.model;

import java.io.Serializable;

public class CartProduct implements Serializable {
    private Product product;
    private int amount;

    public CartProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public CartProduct() {
    }



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
