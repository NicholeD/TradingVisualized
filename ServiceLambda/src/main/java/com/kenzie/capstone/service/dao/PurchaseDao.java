package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.PurchasedStockRecord;

import java.util.List;

public class PurchaseDao {
    private DynamoDBMapper mapper;

    public PurchaseDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public PurchasedStockRecord addPurchase(PurchasedStockRecord record) {
        try {
            //TODO - not sure if this is right...userId or recordId? Do we even need this?
            mapper.save(record, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
                throw new InvalidDataException("User does not exists");
        }

        return record;
    }

    public List<PurchasedStockRecord> findPurchasedStocks(String userId) {
        PurchasedStockRecord purchaseRecord = new PurchasedStockRecord();
        purchaseRecord.setUserId(userId);

        DynamoDBQueryExpression<PurchasedStockRecord> queryExpression = new DynamoDBQueryExpression<PurchasedStockRecord>()
                .withHashKeyValues(purchaseRecord)
                .withIndexName("UserIdRecord")
                .withConsistentRead(false);

        return mapper.query(PurchasedStockRecord.class, queryExpression);
    }
}
