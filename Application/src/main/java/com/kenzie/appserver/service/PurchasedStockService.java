package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.service.model.PurchasedStock;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasedStockService {
    private PurchasedStockRepository purchasedStockRepository;

    public PurchasedStockService(PurchasedStockRepository purchasedStockRepository) {
        this.purchasedStockRepository = purchasedStockRepository;
        }

    public PurchasedStock purchaseStock(String stock, int qty) { //TODO - "String" needs to be "Stock", waiting for Stock model to be created
        if (qty <=0) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Qty has to be greater than 0, one simply cannot purchase nothing")
        }

        PurchasedStockRecord purchasedStockRecord = new PurchasedStockRecord();
        //TODO waiting on Stock model class to implement this:
//        purchasedStockRecord.setName(stock.getName());
//        purchasedStockRecord.setSymbol(stock.getSymbol());
        purchasedStockRecord.setDateOfPurchase(LocalDate.now().toString()); //symantics are wrong here
//        purchasedStockRecord.setPurchasePrice(stock.getPrice());
//        purchasedStockRecord.setShares(qty);

        purchasedStockRepository.save(purchasedStockRecord);

        return new PurchasedStock(purchasedStockRecord.getName(),
        purchasedStockRecord.getSymbol(),
        purchasedStockRecord.getDateOfPurchase(),
        purchasedStockRecord.getPurchasePrice(),
        purchasedStockRecord.getShares());
        }

    public List<PurchasedStock> findByStockSymbol(String symbol) {
            List<PurchasedStockRecord> purchasedStockRecords = purchasedStockRepository
        .findByStockSymbol(symbol);

            List<PurchasedStock> purchasedStock = new ArrayList<>();

            for (PurchasedStockRecord record : purchasedStockRecords) {
                purchasedStock.add(new PurchasedStock(record.getName(), record.getSymbol(),
        record.getDateOfPurchase(), record.getPurchasePrice(), record.getShares()));
        }

            return purchasedStock;

        }

 }