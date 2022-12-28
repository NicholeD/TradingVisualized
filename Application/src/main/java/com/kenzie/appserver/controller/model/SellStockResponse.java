package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class SellStockResponse {
    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotEmpty
    @JsonProperty("recordId")
    private UUID recordID;

    @JsonProperty("stockSymbol")
    private String stockSymbol;

    @JsonProperty("name")
    private String stockName;

    @JsonProperty("salePrice")
    private double salePrice;

    @JsonProperty("shares")
    private int shares;

    @JsonProperty("sellDate")
    private String sellStockDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getRecordID() {
        return recordID;
    }

    public void setRecordID(UUID recordID) {
        this.recordID = recordID;
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

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getSellStockDate() {
        return sellStockDate;
    }

    public void setSellStockDate(String sellStockDate) {
        this.sellStockDate = sellStockDate;
    }
}
