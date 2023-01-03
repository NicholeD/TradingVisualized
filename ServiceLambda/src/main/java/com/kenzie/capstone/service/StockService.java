package com.kenzie.capstone.service;

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

    private StockDao stockDao;
    private ExecutorService executor;

    private PurchaseConverter purchaseConverter = new PurchaseConverter();

    @Inject
    public StockService(StockDao stockDao) {
        this.stockDao = stockDao;
        this.executor = Executors.newCachedThreadPool();
    }

    public PurchasedStockResponse setPurchasedStock(PurchaseStockRequest request) {
        if (request == null || request.getUserId() == null || request.getUserId().length() == 0) {
            throw new InvalidDataException("Request must contain a valid userId");
        }
        PurchasedStockRecord record = PurchaseConverter.fromRequestToRecord(request);
        stockDao.addPurchasedStock(record);
        return PurchaseConverter.fromRecordToResponse(record);
    }

    public List<PurchasedStock> getPurchasedStocks(String userId) {
        List<PurchasedStockRecord> stocks = stockDao.findByUserId(userId);

        return stocks.stream()
                .map(PurchaseConverter::fromRecordToPurchasedStock)
                .collect(Collectors.toList());
    }

    public PurchasedStockRecord sellStock(SellStockRequest request){
        PurchasedStockRecord record = stockDao.sellStock(request);

        return record;
    }


}
