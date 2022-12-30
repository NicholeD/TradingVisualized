//package com.kenzie.appserver.service;
//
//import com.kenzie.appserver.controller.model.SellStockRequest;
//import com.kenzie.appserver.repositories.FishRepository;
//import com.kenzie.appserver.repositories.PurchasedStockRepository;
//import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
//import com.kenzie.appserver.repositories.model.SoldStockRecord;
//import com.kenzie.appserver.service.model.SoldStock;
//import com.kenzie.appserver.service.model.Stock;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
// todo - move transactional history method and then delete class
//@Service
//public class SellStockService {
//    private PurchasedStockRepository purchasedStockRepository;
//
//    public SellStockService(PurchasedStockRepository purchasedStockRepository) {
//        this.purchasedStockRepository = purchasedStockRepository;
//    }
//
//    public SoldStockRecord sellStock(SellStockRequest sellStockRequest) {
//
//        if (sellStockRequest.getShares() <= 0) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "Qty has to be greater than 0, one simply cannot sell nothing");
//        }
//
//        //Retrieving record to update with new qty or delete
//        Optional<PurchasedStockRecord> purchasedStockRecord = purchasedStockRepository.findById(
//                sellStockRequest.getRecordId().toString());
//
//        int ownedShares = purchasedStockRecord.get().getShares();
//
//        if (sellStockRequest.getShares() < ownedShares) {
//            purchasedStockRecord.get().setShares((ownedShares - sellStockRequest.getShares()));
//            //saving over the record for ease rather than implementing @Transactional
//            purchasedStockRepository.save(purchasedStockRecord.get());
//
//        } else if (sellStockRequest.getShares() == ownedShares) {
//            purchasedStockRepository.delete(purchasedStockRecord.get());
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One cannot simply sell more than one owns.");
//        }
//
//        return requestToSellRecord(sellStockRequest, purchasedStockRecord.get());
//
//    }
//
//    //TODO - if we want transactional history
////    public List<SoldStock> findByUserId(String userId) {
////        List<SoldStockRecord> soldRecords = soldStockRepository.findByUserId(userId);
////
////        List<SoldStock> soldStocks = new ArrayList<>();
////
////        for (SoldStockRecord record : soldRecords) {
////            Stock stock = new Stock(record.getStockSymbol(), record.getStockName(),
////                    record.getPurchasedStockPrice(), record.getShares(), record.getDateOfPurchase());
////
////            soldStocks.add(new SoldStock(record.getUserId(), record.getRecordId(),
////                    stock, record.getDateOfSale()));
////        }
////
////        return soldStocks;
////    }
//
//    private PurchasedStockRecord requestToRecord(SellStockRequest request) {
//        PurchasedStockRecord record = new PurchasedStockRecord(request.getUserId(),
//                request.getStockName(), request.getStockSymbol(),
//                request.getSellStockDate(), request.getsalePrice(), request.getShares());
//
//        return record;
//    }
//
//    private SoldStockRecord requestToSellRecord(SellStockRequest request, PurchasedStockRecord record) {
//            SoldStockRecord soldRecord = new SoldStockRecord(request.getUserId(), record.getRecordId(),
//                    request.getStockName(), request.getStockSymbol(), record.getDateOfPurchase(),
//                    request.getSellStockDate(), record.getPurchasePrice(), request.getsalePrice(), request.getShares());
//
//            return soldRecord;
//    }
//
//}
