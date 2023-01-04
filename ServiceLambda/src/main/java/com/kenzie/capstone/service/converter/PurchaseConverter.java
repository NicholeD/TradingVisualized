package com.kenzie.capstone.service.converter;

import com.kenzie.capstone.service.model.*;

import java.time.LocalDate;

public class PurchaseConverter {

    public static PurchasedStockRecord fromRequestToRecord(PurchaseStockRequest request) {
        return new PurchasedStockRecord(request.getUserId(), request.getName(),
                request.getSymbol(), request.getPurchaseDate(), request.getPurchasePrice(), request.getShares());

    }

    public static PurchasedStockResponse fromRecordToResponse(PurchasedStockRecord record) {
        PurchasedStockResponse response = new PurchasedStockResponse();
        response.setUserId(record.getUserId());
        response.setStockSymbol(record.getSymbol());
        response.setPurchasePrice(record.getPurchasePrice());
        response.setShares(record.getShares());
        response.setPurchaseDate(record.getDateOfPurchase());

        return response;
    }

    public static PurchasedStock fromRecordToPurchasedStock(PurchasedStockRecord record) {
        Stock stock = new Stock(record.getSymbol(), record.getName(), record.getPurchasePrice(),
                record.getShares(), record.getDateOfPurchase());

        return new PurchasedStock(record.getUserId(), stock, record.getDateOfPurchase());
    }

    public static SellStockResponse fromRecordToSellStock(PurchasedStockRecord record){
        SellStockResponse response = new SellStockResponse();
        response.setUserId(record.getUserId());
        response.setSymbol(record.getSymbol());
        response.setName(record.getName());
        response.setSalePrice(record.getPurchasePrice());
        response.setShares(record.getShares());
        response.setSellDate(LocalDate.now().toString());
        return response;
    }
}
