package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.StockService;
import com.kenzie.capstone.service.converter.JsonStringToPurchasedStockConverter;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStockResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

//public class DyanoDBStream implements RequestHandler<DynamodbEvent, Void> {
////
////    @Override
////    public Void handleRequest(DynamodbEvent event, Context context) {
////        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
////            String eventName = record.getEventName();
////            if (eventName.equals("INSERT") || eventName.equals("MODIFY")) {
////                Map<String, AttributeValue> map = record.getDynamodb().getNewImage();
////
////                Fish fish = new Fish();
////
////            } else if (eventName.equals("REMOVE")) {
////                // process the stream record for removed item
////            }
////        }
////        return null;
////    }
//    }


