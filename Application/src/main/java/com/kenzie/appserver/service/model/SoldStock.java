package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.StockRecord;

import java.util.UUID;

public class SoldStock {

    private String userId;
    private UUID recordId;

    private String symbol;
    private String name;
    private double soldPrice;
    private int quantity;
    public String soldDate;

    public SoldStock(String userId, UUID recordId, String symbol,
                     String name, double soldPrice, int quantity,
                     String soldDate) {
        this.userId = userId;
        this.recordId = recordId;
        this.symbol = symbol;
        this.name = name;
        this.soldPrice = soldPrice;
        this.quantity = quantity;
        this.soldDate = soldDate;
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

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
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

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }
}
