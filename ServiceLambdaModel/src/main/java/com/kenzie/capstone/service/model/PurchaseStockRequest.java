package com.kenzie.capstone.service.model;

//TODO - need to delete bc now in lambda??
public class PurchaseStockRequest {

    private String userId;

    private String symbol;

    private String name;

    private double purchasePrice;

    private int shares;

    private String purchaseDate;

    public PurchaseStockRequest(){}
    public PurchaseStockRequest(String userId, String symbol,
                                String name, double purchasePrice,
                                int shares, String purchaseDate) {
        this.userId = userId;
        this.symbol = symbol;
        this.name =  name;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "PurchaseStockRequest{" +
                "userId='" + userId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", shares=" + shares +
                ", purchaseDate='" + purchaseDate + '\'' +
                '}';
    }
}
