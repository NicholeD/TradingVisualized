package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.SellStockRequest;
import com.kenzie.appserver.controller.model.SellStockResponse;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.service.SellStockService;
import com.kenzie.appserver.service.model.SoldStock;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchasedstocks")
public class SellStockController {
    private SellStockService sellStockService;

    SellStockController(SellStockService sellStockService) {
        this.sellStockService = sellStockService;
    }

    @PostMapping
    public ResponseEntity<SellStockResponse> sellStock(@RequestBody SellStockRequest sellStockRequest) {
        //TODO

        //sell stock
        SoldStockRecord soldStockRecord = sellStockService.sellStock(sellStockRequest);

        Stock stock = recordToStock(soldStockRecord);

        SoldStock soldStock = new SoldStock(sellStockRequest.getUserId(),
                sellStockRequest.getRecordId(), stock, LocalDate.now().toString());

        SellStockResponse response = createSellStockResponse(soldStock);

        return ResponseEntity.ok(response);
    }

    //TODO - if we want transactional history
//    @GetMapping
//    public ResponseEntity<List<SellStockResponse>> getAllSoldStocks(@PathVariable("userId") String userId) {
//        List<SoldStock> soldStocks = sellStockService.findByUserId(userId);
//
//        if (soldStocks == null || soldStocks.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        List<SellStockResponse> responses = new ArrayList<>();
//
//        for (SoldStock stock : soldStocks) {
//            responses.add(createSellStockResponse(stock));
//        }
//
//        return ResponseEntity.ok(responses);
//    }

    private Stock recordToStock(SoldStockRecord record) {
        return new Stock(record.getStockSymbol(), record.getStockName(),
                record.getSaleStockPrice(), record.getShares(), record.getDateOfSale());
    }

    private SellStockResponse createSellStockResponse(SoldStock soldStock) {
        SellStockResponse sellStockResponse = new SellStockResponse();
        sellStockResponse.setUserId(soldStock.getUserId());
        sellStockResponse.setRecordID(soldStock.getRecordId());
        sellStockResponse.setStockSymbol(soldStock.getStock().getSymbol());
        sellStockResponse.setStockName(soldStock.getStock().getName());
        sellStockResponse.setSalePrice(soldStock.getStock().getPurchasePrice());
        sellStockResponse.setShares(soldStock.getStock().getQuantity());
        sellStockResponse.setSellStockDate(soldStock.getSoldDate());

        return sellStockResponse;
    }
}
