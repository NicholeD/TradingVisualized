package com.kenzie.capstone.service;

import com.kenzie.capstone.service.caching.CachingStockDao;
import com.kenzie.capstone.service.converter.PurchaseConverter;
import com.kenzie.capstone.service.dao.StockDao;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.lambda.SellStock;
import com.kenzie.capstone.service.model.*;


import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class StockService {

    private CachingStockDao cachingStockDao;
    private ExecutorService executor;
    private PurchaseConverter purchaseConverter = new PurchaseConverter();

    @Inject
    public StockService(CachingStockDao cachingStockDao) {
        this.cachingStockDao = cachingStockDao;
        this.executor = Executors.newCachedThreadPool();
    }

    public PurchasedStockResponse setPurchasedStock(PurchaseStockRequest request) {
        if (request == null || request.getUserId() == null || request.getUserId().length() == 0) {
            throw new InvalidDataException("Request must contain a valid userId");
        }
        PurchasedStockRecord record = PurchaseConverter.fromRequestToRecord(request);
        System.out.println( "IN STOCKSERVICE SETPURCHASEDSTOCK: " + record.toString());
        cachingStockDao.addPurchasedStock(record);
        return PurchaseConverter.fromRecordToResponse(record);
    }

    public List<PurchasedStock> getPurchasedStocks(String userId) {
        List<PurchasedStockRecord> stocks = cachingStockDao.findByUserId(userId);

        return stocks.stream()
                .map(PurchaseConverter::fromRecordToPurchasedStock)
                .collect(Collectors.toList());
    }

    public SellStockResponse sellStock(SellStockRequest request){
        PurchasedStockRecord record = cachingStockDao.sellStock(request);

        return PurchaseConverter.fromRecordToSellStock(record);
    }
}
