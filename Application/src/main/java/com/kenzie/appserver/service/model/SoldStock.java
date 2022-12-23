package com.kenzie.appserver.service.model;

import java.util.UUID;

public class SoldStock {

    private String userId;
    private UUID recordId;
    public Stock stock;
    public String soldDate;

    public SoldStock(String userId, UUID recordId, Stock stock, String soldDate) {
        this.userId = userId;
        this.recordId = recordId;
        this.stock = stock;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }
}
