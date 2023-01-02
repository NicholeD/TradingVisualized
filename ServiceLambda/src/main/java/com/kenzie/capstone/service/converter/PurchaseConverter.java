package com.kenzie.capstone.service.converter;

import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.Stock;
import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.PurchasedStockResponse;

public class PurchaseConverter {

    public static PurchasedStockRecord fromRequestToRecord(PurchaseStockRequest request) {
        return new PurchasedStockRecord(request.getUserId(), request.getStockName(),
                request.getStockSymbol(), request.getPurchaseDate(), request.getPurchasePrice(), request.getShares());

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
}
