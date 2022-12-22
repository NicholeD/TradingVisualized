package com.kenzie.appserver.repositories.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

//TODO for future rollout features: 'sold' table as part of a user's transaction history
//@DynamoDBTable(tableName = "sold")
public class SoldStockRecord {
    private String sellerUserId;
    private String stockName;
    private String stockSymbol;
    private String dateOfSale;
    private String dateOfPurchase;
    private Double purchasedStockPrice;
    private Double saleStockPrice;
    private Double saleTotal;
    private int shares;
    private Double realizedProfit;

    //@DynamoDBHashKey(attributeName = "UserId")
    public String getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
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

    private void setSaleTotal() {
        this.saleTotal = getSaleStockPrice() * getShares();
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
