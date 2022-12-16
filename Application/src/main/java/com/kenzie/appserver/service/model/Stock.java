package com.kenzie.appserver.service.model;

public class Stock {
    private final String symbol;
    private final String name;
    private final double purchasePrice;
    private int shares;
    private final String purchaseDate;

    public Stock(String symbol, String name, double purchasePrice, int shares, String purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
        this.purchaseDate = purchaseDate;
    }

    //TODO - do we need a constructor that assigns the shares to 1?
    public Stock(String symbol, String name, double purchasePrice, String purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.shares = 1;
        this.purchaseDate = purchaseDate;
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

    public int getShares() {
        return shares;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
