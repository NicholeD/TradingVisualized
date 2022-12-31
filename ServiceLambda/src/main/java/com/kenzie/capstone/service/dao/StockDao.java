package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.PurchasedStockRecord;

import java.util.List;

public class StockDao implements Dao {
    private DynamoDBMapper mapper;

    public StockDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public PurchasedStockRecord addPurchasedStock(PurchasedStockRecord record) {
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

    public List<PurchasedStockRecord> findByUserId(String userId) {
        PurchasedStockRecord purchaseRecord = new PurchasedStockRecord();
        purchaseRecord.setUserId(userId);

        DynamoDBQueryExpression<PurchasedStockRecord> queryExpression = new DynamoDBQueryExpression<PurchasedStockRecord>()
                .withHashKeyValues(purchaseRecord)
                .withIndexName("UserIdRecord")
                .withConsistentRead(false);

        return mapper.query(PurchasedStockRecord.class, queryExpression);
    }
}
