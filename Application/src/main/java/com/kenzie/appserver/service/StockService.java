package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.repositories.model.FishRecord;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.repositories.model.StockRecord;
import com.kenzie.appserver.service.model.Stock;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StockService {
    private RestTemplate restTemplate = new RestTemplate();
    private StockRepository stockRepository;
    private FishRepository fishRepository;
    private StockServiceClient stockServiceClient;

    public StockService(StockRepository stockRepository, FishRepository fishRepository, StockServiceClient stockServiceClient) {
        this.stockRepository = stockRepository;
        this.fishRepository = fishRepository;
        this.stockServiceClient = stockServiceClient;
    }

    @GetMapping
    public StockResponse getStocksBySymbol(String symbol) {
        String url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&apikey=5DA1NYHTSAKVQ99Z", symbol);

        StockResponse stockResponse = restTemplate.getForObject(url, StockResponse.class);

        return stockResponse;
    }

    @GetMapping("/Name")
    public String getStockNameBySymbol(String symbol) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&apikey=5DA1NYHTSAKVQ99Z&symbol=" + symbol;

        Map<String, String> response = restTemplate.getForObject(url, Map.class);

        return response.get("Name");
    }

    public List<Stock> findByUserId(String userId) {
        List<PurchasedStockRecord> stockRecords = stockRepository
                .findByUserId(userId);

        List<Stock> purchasedStock = new ArrayList<>();

        for (PurchasedStockRecord record : stockRecords) {
            purchasedStock.add(recordToStock(record));
        }

        return purchasedStock;

    }

    public PurchasedStockResponse purchaseStock(PurchaseStockRequest purchaseStockRequest) {
        if (purchaseStockRequest.getShares() <=0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Qty has to be greater than 0, one simply cannot purchase nothing");
        }

        //Checking if userId already owns requested stock
        List<Stock> stocks = findByUserId(purchaseStockRequest.getUserId());
        PurchaseStockRequest updatedRequest = purchaseStockRequest;

        for (Stock stock : stocks) {
            //If user already owns stock, update request with owned stock shares + request shares
            if (stock.getSymbol().equals(purchaseStockRequest.getSymbol())) {
                updatedRequest.setShares(stock.getQuantity() + purchaseStockRequest.getShares());
            }
        }

        PurchasedStockRecord record = purchaseRequestToRecord(updatedRequest);
        stockServiceClient.addPurchasedStock(updatedRequest);
        stockRepository.save(record);

        FishRecord fishRecord= new FishRecord();
        fishRecord.setName(record.getName());
        fishRecord.setPrice(record.getPurchasePrice());
        fishRecord.setQuantity(record.getShares());
        fishRepository.save(fishRecord);

        PurchasedStockResponse response = recordToResponse(record);

        return response;
    }

    public SoldStockRecord sellStock(SellStockRequest sellStockRequest) {

        if (sellStockRequest.getShares() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Qty has to be greater than 0, one simply cannot sell nothing");
        }

        SellStockResponse record = stockServiceClient.sellStock(sellStockRequest);

        return requestToSellRecord(sellStockRequest, record);
    }

    /** Helpers */
    private PurchasedStockRecord purchaseRequestToRecord(PurchaseStockRequest request) {
        PurchasedStockRecord stockRecord = new PurchasedStockRecord();
        stockRecord.setUserId(request.getUserId());
        stockRecord.setSymbol(request.getSymbol());
        stockRecord.setName(request.getName());
        stockRecord.setPurchasePrice(request.getPurchasePrice());
        stockRecord.setShares(request.getShares());
        stockRecord.setDateOfPurchase(request.getPurchaseDate());

//        stockRepository.save(stockRecord);

        return stockRecord;
    }

    private Stock recordToStock(PurchasedStockRecord record) {
        Stock stock = new Stock(record.getSymbol(), record.getName(), record.getPurchasePrice(),
                record.getShares(), record.getDateOfPurchase());
        stock.setUserId(record.getUserId());

        return stock;
    }

    private SoldStockRecord requestToSellRecord(SellStockRequest request, SellStockResponse record) {
        SoldStockRecord soldRecord = new SoldStockRecord(request.getUserId(),
                request.getStockName(), request.getStockSymbol(), record.getSellDate(),
                request.getSellStockDate(), record.getSalePrice(), request.getsalePrice(), request.getShares());

        return soldRecord;
    }

    private PurchasedStockResponse recordToResponse(PurchasedStockRecord record) {
        PurchasedStockResponse response = new PurchasedStockResponse();
        response.setUserId(record.getUserId());
        response.setStockSymbol(record.getSymbol());
        response.setPurchasePrice(record.getPurchasePrice());
        response.setShares(record.getShares());
        response.setPurchaseDate(record.getDateOfPurchase());

        return response;
    }
}
