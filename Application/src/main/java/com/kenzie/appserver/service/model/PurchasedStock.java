package com.kenzie.appserver.service.model;

public class PurchasedStock {
    private String userId;

    public Stock stock;
    public String orderDate;

    public PurchasedStock(String userId, Stock stock, String orderDate) {
        this.userId = userId;
        this.stock = stock;
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
