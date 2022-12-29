package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;
//TODO - need to delete bc now in lambda??
@DynamoDBTable(tableName = "purchased")
public class PurchasedStockRecord {

    private String userId;
    private UUID recordId;
    private String name;
    private String symbol;
    private String dateOfPurchase;
    private Double purchasePrice;
    private int shares;

    public PurchasedStockRecord(String userId, String name, String symbol,
                                String dateOfPurchase, Double purchasePrice,
                                int shares) {
        this.userId = userId;
        this.recordId = UUID.randomUUID();
        this.name = name;
        this.symbol = symbol;
        this.dateOfPurchase = dateOfPurchase;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
    }

    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBRangeKey(attributeName = "RecordId")
    public UUID getRecordId() { return recordId; }

    public void setRecordId() { this.recordId = getRecordId(); }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @DynamoDBAttribute(attributeName = "DateOfPurchase")
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @DynamoDBAttribute(attributeName = "PurchasePrice")
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

}