package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

@DynamoDBTable(tableName = "Portfolio")
public class PurchasedStockRecord {

    private String userId;
    private String recordId;
    private String name;
    private String symbol;
    private String dateOfPurchase;
    private Double purchasePrice;
    private int shares;
    public PurchasedStockRecord(){}
    public PurchasedStockRecord(String userId, String name, String symbol,
                                String dateOfPurchase, Double purchasePrice,
                                int shares) {
        this.userId = userId;
        this.recordId = UUID.randomUUID().toString();
        this.name = name;
        this.symbol = symbol;
        this.dateOfPurchase = dateOfPurchase;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userIdRecord")
    @DynamoDBAttribute(attributeName = "id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "recordId")
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @DynamoDBAttribute(attributeName = "purchaseDate")
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @DynamoDBAttribute(attributeName = "purchasePrice")
    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "PurchasedStockRecord{" +
                "userId='" + userId + '\'' +
                ", recordId='" + recordId + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", dateOfPurchase='" + dateOfPurchase + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", shares=" + shares +
                '}';
    }
}