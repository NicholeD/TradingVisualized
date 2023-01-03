package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.exceptions.ResponseStatusException;
import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.SellStockRequest;



import java.util.List;
import java.util.Optional;

public class StockDao implements Dao {
    private DynamoDBMapper mapper;

    public StockDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public PurchasedStockRecord addPurchasedStock(PurchasedStockRecord record) {
        try {
            mapper.save(record);
//            //TODO - not sure if this is right...userId or recordId? Do we even need this?
//            mapper.save(record, new DynamoDBSaveExpression()
//                    .withExpected(ImmutableMap.of(
//                            "userId",
//                            new ExpectedAttributeValue().withExists(false)
//                    )));
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
//                .withIndexName("UserIdRecord")
                .withConsistentRead(false);

        return mapper.query(PurchasedStockRecord.class, queryExpression);
    }

    public PurchasedStockRecord sellStock(SellStockRequest request){
        if (request.getShares() <= 0) {
            throw new ResponseStatusException(
                    "Qty has to be greater than 0, one simply cannot sell nothing");
        }
        System.out.println("IN SELL STOCK");
        //Retrieving record to update with new qty or delete
        PurchasedStockRecord record = new PurchasedStockRecord();
        record.setSymbol(request.getStockSymbol());

        PurchasedStockRecord purchasedStockRecord = mapper.load(PurchasedStockRecord.class, record);
        int ownedShares = purchasedStockRecord.getShares();

        if (request.getShares() < ownedShares) {
            purchasedStockRecord.setShares((ownedShares - request.getShares()));
            //saving over the record for ease rather than implementing @Transactional
            mapper.save(purchasedStockRecord);

        } else if (request.getShares() == ownedShares) {
            mapper.delete(purchasedStockRecord);
        } else {
            throw new ResponseStatusException( "One cannot simply sell more than one owns.");
        }

        return purchasedStockRecord;
    }
}
