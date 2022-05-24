package com.example.finalproject.model;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private Cart cart;
    private Account account;
    private String address;
    private String phoneNumber;

    public Order() {
    }

    public Order(int id, Cart cart, Account account, String address, String phoneNumber) {
        this.id = id;
        this.cart = cart;
        this.account = account;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", account=" + account +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
