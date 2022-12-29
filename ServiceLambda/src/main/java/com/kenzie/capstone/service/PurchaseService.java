package com.kenzie.capstone.service;

import com.kenzie.capstone.service.converter.PurchaseConverter;
import com.kenzie.capstone.service.dao.PurchaseDao;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.PurchasedStockResponse;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PurchaseService {

    private PurchaseDao purchaseDao;
    private ExecutorService executor;

    private PurchaseConverter purchaseConverter = new PurchaseConverter();

    @Inject
    public PurchaseService(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
        this.executor = Executors.newCachedThreadPool();
    }

    public PurchasedStockResponse addPurchasedStock(PurchaseStockRequest request) {
        if (request == null || request.getUserId() == null || request.getUserId().length() == 0) {
            throw new InvalidDataException("Request must contain a valid userId");
        }
        PurchasedStockRecord record = PurchaseConverter.fromRequestToRecord(request);
        purchaseDao.addPurchase(record);
        return PurchaseConverter.fromRecordToResponse(record);
    }

    public List<PurchasedStock> getPurchasedStocks(String userId) {
        List<PurchasedStockRecord> stocks = purchaseDao.findPurchasedStocks(userId);

        return stocks.stream()
                .map(PurchaseConverter::fromRecordToPurchasedStock)
                .collect(Collectors.toList());
    }


}
