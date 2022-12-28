package com.kenzie.capstone.service.model;

//TODO - need to delete bc now in lambda??
public class PurchaseStockRequest {

    private String userId;

    private String stockSymbol;

    private String stockName;

    private double purchasePrice;

    private int shares;

    private String purchaseDate;


    public PurchaseStockRequest(String userId, String stockSymbol,
                                String stockName, double purchasePrice,
                                int shares, String purchaseDate) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
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

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

}
