package com.kenzie.capstone.service.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.PurchaseStockRequest;

public class JsonStringToPurchasedStockConverter {

    public PurchaseStockRequest convert(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PurchaseStockRequest purchaseRequest = gson.fromJson(body, PurchaseStockRequest.class);
            return purchaseRequest;
        } catch (Exception e) {
            throw new InvalidDataException("Purchase could not be deserialized");
        }
    }
}
