package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "fish")
public class FishRecord {

    @DynamoDBHashKey(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "size")
    private float size;

    @DynamoDBAttribute(attributeName = "quantity")
    private double quantity;

    @DynamoDBAttribute(attributeName = "price")
    private double price;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FishRecord fishRecord = (FishRecord) o;
        return Objects.equals(name, fishRecord.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
