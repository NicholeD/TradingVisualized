package com.kenzie.appserver.controller.model;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

//TODO - need to delete bc now in lambda??
public class PurchaseStockRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("stockSymbol")
    private String stockSymbol;

    @JsonProperty("stockName")
    private String stockName;

    @JsonProperty("purchasePrice")
    private double purchasePrice;

    @JsonProperty("shares")
    private int shares;

    @JsonProperty("purchaseDate")
    private String purchaseDate;

    @JsonProperty("orderDate")
    private String orderDate;

    public PurchaseStockRequest(String userId, String stockSymbol,
                                String stockName, double purchasePrice,
                                int shares, String purchaseDate, String orderDate) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
        this.purchaseDate = purchaseDate;
        this.orderDate = orderDate;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
