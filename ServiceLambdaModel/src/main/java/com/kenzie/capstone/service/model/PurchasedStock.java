package com.kenzie.capstone.service.model;

public class PurchasedStock {
    private String userId;

    public Stock stock;
    public String purchasedDate;

    public PurchasedStock(){}
    public PurchasedStock(String userId, Stock stock, String purchasedDate) {
        this.userId = userId;
        this.stock = stock;
        this.purchasedDate = purchasedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getpurchasedDate() {
        return purchasedDate;
    }

    public void setpurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }
}