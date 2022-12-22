package com.kenzie.appserver.service;

import com.amazonaws.services.ec2.model.Purchase;
import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.repositories.model.FishRecord;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;

import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class PurchasedStockService {
    private PurchasedStockRepository purchasedStockRepository;
    private FishRepository fishRepository;

    public PurchasedStockService(PurchasedStockRepository purchasedStockRepository,
                                 FishRepository fishRepository) {
        this.purchasedStockRepository = purchasedStockRepository;
        this.fishRepository = fishRepository;
        }

    public PurchasedStock purchaseStock(PurchaseStockRequest purchaseStockRequest) {
        if (purchaseStockRequest.getShares() <=0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Qty has to be greater than 0, one simply cannot purchase nothing");
        }

        PurchasedStockRecord record = purchaseRequestToRecord(purchaseStockRequest);
        purchasedStockRepository.save(record);

        FishRecord fishRecord= new FishRecord();
        fishRecord.setName(record.getName());
        fishRecord.setPrice(record.getPurchasePrice());
        fishRecord.setQuantity(record.getShares());
        fishRepository.save(fishRecord);

        PurchasedStock purchasedStock = recordToStock(record);

        return purchasedStock;
    }


// why did i write this method in this service class again?? wouldn't this be under a portfolio service class??

    public List<PurchasedStock> findByStockSymbol(String symbol) {
            List<PurchasedStockRecord> purchasedStockRecords = purchasedStockRepository
        .findByStockSymbol(symbol);

            List<PurchasedStock> purchasedStock = new ArrayList<>();

            for (PurchasedStockRecord record : purchasedStockRecords) {
                purchasedStock.add(recordToStock(record));
        }

            return purchasedStock;

        }

    private PurchasedStockRecord purchaseRequestToRecord(PurchaseStockRequest request) {
        PurchasedStockRecord purchasedStockRecord = new PurchasedStockRecord(request.getUserId(),
                request.getStockName(), request.getStockSymbol(), LocalDate.now().toString(),
                request.getPurchasePrice(), request.getShares());

        purchasedStockRepository.save(purchasedStockRecord);

        return purchasedStockRecord;
    }

    private PurchasedStock recordToStock(PurchasedStockRecord record) {
        Stock stock = new Stock(record.getSymbol(), record.getName(), record.getPurchasePrice(),
                record.getShares(), record.getDateOfPurchase());

        return new PurchasedStock(record.getUserId(), stock, stock.getPurchaseDate());
    }
 }

