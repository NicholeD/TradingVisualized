package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
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

public class AddPurchase implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        JsonStringToPurchasedStockConverter jsonStringToPurchasedStockConverter = new JsonStringToPurchasedStockConverter();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //for debugging:
        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        StockService stockService = serviceComponent.provideStockService();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        System.out.println(input.getBody());
        try {
            PurchaseStockRequest purchaseStockRequest = jsonStringToPurchasedStockConverter.convert(input.getBody());
            PurchasedStockResponse purchasedStockResponse = stockService.setPurchasedStock(purchaseStockRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(purchasedStockResponse));
        } catch (InvalidDataException e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.errorPayload()));
        }
    }
}
