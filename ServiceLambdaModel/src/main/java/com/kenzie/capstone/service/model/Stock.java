package com.kenzie.capstone.service.model;

public class Stock {

    private String userId;
    private String symbol;
    private String name;
    private double purchasePrice;
    private int quantity;
    private String purchaseDate;

    public Stock(String symbol, String name, double purchasePrice, int quantity, String purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }
    public Stock(){}

    public Stock(String symbol, String name, double purchasePrice, String purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = 1;
        this.purchaseDate = purchaseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
