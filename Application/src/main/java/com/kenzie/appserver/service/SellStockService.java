package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.SellStockRequest;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class SellStockService {
    private PurchasedStockRepository purchasedStockRepository;
    private FishRepository fishRepository;

    public SellStockService(PurchasedStockRepository purchasedStockRepository,
                            FishRepository fishRepository) {
        this.purchasedStockRepository = purchasedStockRepository;
        this.fishRepository = fishRepository;
    }

    public SoldStockRecord sellStock(SellStockRequest sellStockRequest) {
        if (sellStockRequest.getShares() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Qty has to be greater than 0, one simply cannot sell nothing");
        }
        //TODO - request to purchasedStockRecord
        PurchasedStockRecord purchasedStockRecord = purchasedStockRepository.
        PurchasedStockRecord newRecord = requestToRecord(sellStockRequest);


        //TODO - update purchasedStockRepository quantity of stock - throw exceptions if request is greater than owned shares
        //TODO - add sold price $ (current price * shares) to user funds
    }

    private PurchasedStockRecord requestToRecord(SellStockRequest request) {
        PurchasedStockRecord record = new PurchasedStockRecord();
        record.setUserId(request.getUserId());
        record.setName(request.getStockName());
        record.setSymbol(request.getStockSymbol());
        record.setDateOfPurchase(request.getPurchaseDate());
        record.setShares(request.getShares());
        //not setting purchase price (request contains current sale price & record contains past price)

        return record;
    }

    private SoldStockRecord purchaseToSellRecord(PurchasedStockRecord purchasedStockRecord, SellStockRequest request) {
            SoldStockRecord soldRecord = new SoldStockRecord();
            soldRecord.setSellerUserId(purchasedStockRecord.getUserId());
            soldRecord.setStockName(purchasedStockRecord.getName());
            soldRecord.setStockSymbol(purchasedStockRecord.getSymbol());
            soldRecord.setDateOfSale(LocalDate.now().toString());
            soldRecord.setDateOfPurchase(purchasedStockRecord.getDateOfPurchase());
            soldRecord.setPurchasedStockPrice(purchasedStockRecord.getPurchasePrice());
            soldRecord.setSalePrice(request.getsalePrice());
            soldRecord.setShares(purchasedStockRecord.getShares());

            return soldRecord;
    }

}
