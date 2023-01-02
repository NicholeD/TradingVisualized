package com.kenzie.appserver.repositories.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.util.UUID;

//TODO - of we want a user's transaction history (uncomment & create SoldStockRepository)
//@DynamoDBTable(tableName = "sold")
public class SoldStockRecord {
    private String userId;
    private String stockName;
    private String stockSymbol;
    private String dateOfSale;
    private String dateOfPurchase;
    private Double purchasedStockPrice;
    private Double saleStockPrice;
    private Double saleTotal;
    private int shares;
    private Double realizedProfit;

    public SoldStockRecord(String userId, String stockName, String stockSymbol,
                           String dateOfSale, String dateOfPurchase, Double purchasedStockPrice, Double saleStockPrice,
                           int shares) {
        this.userId = userId;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
        this.dateOfSale = dateOfSale;
        this.dateOfPurchase = dateOfPurchase;
        this.purchasedStockPrice = purchasedStockPrice;
        this.saleStockPrice = saleStockPrice;
        this.saleTotal = saleStockPrice * shares;
        this.shares = shares;
        this.realizedProfit = (purchasedStockPrice * shares) - this.saleTotal;
    }

    //@DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //@DynamoDBAttribute(attributeName = "Name")
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    //@DynamoDBAttribute(attributeName = "Symbol")
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    //@DynamoDBAttribute(attributeName = "DateOfSale")
    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    //@DynamoDBAttribute(attributeName = "DateOfPurchase")
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    //@DynamoDBAttribute(attributeName = "PurchasePrice")
    public Double getPurchasedStockPrice() {
        return purchasedStockPrice;
    }

    public void setPurchasedStockPrice(Double purchasedStockPrice) {
        this.purchasedStockPrice = purchasedStockPrice;
    }

    //@DynamoDBAttribute(attributeName = "SalePrice")
    public Double getSaleStockPrice() {
        return saleStockPrice;
    }

    public void setSalePrice(Double saleStockPrice) {
        this.saleStockPrice = saleStockPrice;
    }

    //@DynamoDBAttribute(attributeName = "saleTotal")
    public Double getSaleTotal() {
        return saleTotal;
    }

    private void setSaleTotal(Double saleTotal) {
        this.saleTotal = saleTotal;
    }

    //@DynamoDBAttribute(attributeName = "quantity")
    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    //@DynamoDBAttribute(attributeName = "realizedProfit")
    public Double getRealizedProfit() {
        return realizedProfit;
    }

    private void setRealizedProfit() {
        this.realizedProfit = getSaleTotal() - (getPurchasedStockPrice() * getShares());
    }
}
