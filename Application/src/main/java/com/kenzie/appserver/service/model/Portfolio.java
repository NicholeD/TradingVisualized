package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private String userId;
    private Double funds;
    private List<Stock> stocks;

    public Portfolio() {
        this.funds = 1000000.0;
        this.stocks = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }
}
