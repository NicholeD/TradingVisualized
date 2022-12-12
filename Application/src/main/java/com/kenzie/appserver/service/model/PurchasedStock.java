package com.kenzie.appserver.service.model;

public class PurchasedStock {
    private final String name;
    private final String symbol;
    private final Date dateOfPurchase;
    private final Double purchasePrice;
    private final int shares;

    public PurchasedStock(String name, String symbol, Date dateOfPurchase, Double purchasePrice, int shares) {
        this.name = name;
        this.symbol = symbol;
        this.dateOfPurchase = dateOfPurchase;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public int getShares() {
        return shares;
    }
}