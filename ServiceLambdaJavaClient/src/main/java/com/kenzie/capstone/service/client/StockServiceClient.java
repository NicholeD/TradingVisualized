package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.PurchasedStockResponse;

import java.util.List;

public class StockServiceClient {
    private static final String ADD_PURCHASE_ENDPOINT = "purchasedstocks/add";
    private static final String GET_PURCHASED_STOCK_ENDPOINT = "purchasedstocks/{userId}";
    private ObjectMapper mapper;

    public StockServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public PurchasedStockResponse addPurchasedStock(PurchaseStockRequest purchaseRequest) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String request;

        try {
            request = mapper.writeValueAsString(purchaseRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        String response = endpointUtility.postEndpoint(ADD_PURCHASE_ENDPOINT, request);
        PurchasedStockResponse purchaseResponse;
        try {
            purchaseResponse = mapper.readValue(response, PurchasedStockResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return purchaseResponse;
    }

    public List<PurchasedStock> getPurchasedStock(String userId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_PURCHASED_STOCK_ENDPOINT.replace("{userId}", userId));
        List<PurchasedStock> stocks;
        try {
            stocks = mapper.readValue(response, new TypeReference<>(){});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return stocks;
    }
}
