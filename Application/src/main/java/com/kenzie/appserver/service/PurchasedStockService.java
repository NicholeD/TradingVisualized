package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
//public class PurchasedStockService {
//    private PurchasedStockRepository purchasedStockRepository;

//    public PurchasedStockService(PurchasedStockRepository purchasedStockRepository) {
//        this.purchasedStockRepository = purchasedStockRepository;
//        }

//    public PurchasedStock purchaseStock(PurchaseStockRequest purchaseStockRequest) { //TODO - "String" needs to be "Stock", waiting for Stock model to be created
//        if (purchaseStockRequest.getShares() <=0) {
//            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Qty has to be greater than 0, one simply cannot purchase nothing")
//        }

    // if stock symbol is invalid throw new IllegalArgumentException (
//
//        PurchasedStockRecord purchasedStockRecord = new PurchasedStockRecord();
//        //TODO waiting on Stock model class to implement this:
////        purchasedStockRecord.setName(purchaseStockRequest.getName());  TODO - cached list of key-value map?? key = symbol, value = name??
////        purchasedStockRecord.setSymbol(purchaseStockRequest.getSymbol());
//        purchasedStockRecord.setDateOfPurchase(LocalDate.now().toString()); //symantics are wrong here
////        purchasedStockRecord.setPurchasePrice(purchaseStockRequest.getPurchasePrice());
////        purchasedStockRecord.setShares(purchaseStockRequest.getShares());
//
//        purchasedStockRepository.save(purchasedStockRecord);
//
//        return new PurchasedStock(purchasedStockRecord.getName(),
//        purchasedStockRecord.getSymbol(),
//        purchasedStockRecord.getDateOfPurchase(),
//        purchasedStockRecord.getPurchasePrice(),
//        purchasedStockRecord.getShares());
//        }
//
//
//
// why did i write this method in this service class again?? wouldn't this be under a portfolio service class??
//    public List<PurchasedStock> findByStockSymbol(String symbol) {
//            List<PurchasedStockRecord> purchasedStockRecords = purchasedStockRepository
//        .findByStockSymbol(symbol);
//
//            List<PurchasedStock> purchasedStock = new ArrayList<>();
//
//            for (PurchasedStockRecord record : purchasedStockRecords) {
//                purchasedStock.add(new PurchasedStock(record.getName(), record.getSymbol(),
//        record.getDateOfPurchase(), record.getPurchasePrice(), record.getShares()));
//        }
//
//            return purchasedStock;
//
//        }
// }