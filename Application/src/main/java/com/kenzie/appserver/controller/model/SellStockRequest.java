package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class SellStockRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotEmpty
    @JsonProperty("recordId")
    private UUID recordId;

    @JsonProperty("stockSymbol")
    private String stockSymbol;

    @JsonProperty("stockName")
    private String stockName;

    @JsonProperty("salePrice")
    private double salePrice;

    @JsonProperty("shares")
    private int shares;

    @JsonProperty("purchaseDate")
    private String purchaseDate;


    public SellStockRequest(String userId, UUID recordId, String stockSymbol, String stockName,
                            double salePrice, int shares, String purchaseDate) {
        this.userId = userId;
        this.recordId = recordId;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.salePrice = salePrice;
        this.shares = shares;
        this.purchaseDate = purchaseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getRecordId() {
        return recordId;
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

    public double getsalePrice() {
        return salePrice;
    }

    public void setsalePrice(double salePrice) {
        this.salePrice = salePrice;
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
