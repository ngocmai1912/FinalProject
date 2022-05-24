package com.example.finalproject.model;

import java.io.Serializable;

public class CartProduct implements Serializable {
    private int id;
    private Product product;
    private int amount;

    public CartProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public CartProduct() {
    }

    public CartProduct(int id, Product product, int amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
