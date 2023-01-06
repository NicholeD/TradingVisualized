package com.kenzie.capstone.service.lambda;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.Fish;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.or;

public class DynamoDBStream implements RequestHandler<DynamodbEvent, Void> {
    @Override
    public Void handleRequest(DynamodbEvent event, Context context) {
        // Process the event
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            if (record.getEventName().equals("INSERT") || (record.getEventName().equals("MODIFY") || (record.getEventName().equals("REMOVE")))) {
            // Get all of our purchasedStocks in our DynamoDBTable
                // Convert those PurchasedStockRecords into a Fish List
                // Call JsonFishConvert.convertToJsonFile on fish list and pass in the data.txt file
                DynamoDBMapper mapper;
                AmazonDynamoDBClient dynamoDBClient;
                mapper = new DynamoDBMapper(dynamoDBClient);
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                List<PurchasedStockRecord> purchasedStocks = mapper.scan(PurchasedStockRecord.class, scanExpression);

                List<Fish> fishList = new ArrayList<>();
                for (PurchasedStockRecord purchasedStock : purchasedStocks) {
                    Fish fish = new Fish(purchasedStock.getUserId(), purchasedStock.getRecordId(), purchasedStock.getName());
                    fishList.add(fish);
                }

                JsonFishConverter.convertToJsonFile(fishList, "data.txt");
            }
        }
    }
}
