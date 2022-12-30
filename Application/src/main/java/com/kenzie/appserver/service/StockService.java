package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.controller.model.SellStockRequest;
import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.repositories.model.FishRecord;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StockService {
    private RestTemplate restTemplate = new RestTemplate();

    private PurchasedStockRepository purchasedStockRepository;
    private FishRepository fishRepository;

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

    //TODO - change implementation to wire to lambda's dao to call api (get rid of above methods and put in lambda)
    public List<PurchasedStock> findByStockSymbol(String symbol) {
        List<PurchasedStockRecord> purchasedStockRecords = purchasedStockRepository
                .findBySymbol(symbol);

        List<PurchasedStock> purchasedStock = new ArrayList<>();

        for (PurchasedStockRecord record : purchasedStockRecords) {
            purchasedStock.add(recordToStock(record));
        }

        return purchasedStock;

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

    public SoldStockRecord sellStock(SellStockRequest sellStockRequest) {

        if (sellStockRequest.getShares() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Qty has to be greater than 0, one simply cannot sell nothing");
        }

        //Retrieving record to update with new qty or delete
        Optional<PurchasedStockRecord> purchasedStockRecord = purchasedStockRepository.findById(
                sellStockRequest.getRecordId().toString());

        int ownedShares = purchasedStockRecord.get().getShares();

        if (sellStockRequest.getShares() < ownedShares) {
            purchasedStockRecord.get().setShares((ownedShares - sellStockRequest.getShares()));
            //saving over the record for ease rather than implementing @Transactional
            purchasedStockRepository.save(purchasedStockRecord.get());

        } else if (sellStockRequest.getShares() == ownedShares) {
            purchasedStockRepository.delete(purchasedStockRecord.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One cannot simply sell more than one owns.");
        }

        return requestToSellRecord(sellStockRequest, purchasedStockRecord.get());

    }

    /** Helpers */
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

    private PurchasedStockRecord requestToPurchasedRecord(SellStockRequest request) {
        PurchasedStockRecord record = new PurchasedStockRecord(request.getUserId(),
                request.getStockName(), request.getStockSymbol(),
                request.getSellStockDate(), request.getsalePrice(), request.getShares());

        return record;
    }

    private SoldStockRecord requestToSellRecord(SellStockRequest request, PurchasedStockRecord record) {
        SoldStockRecord soldRecord = new SoldStockRecord(request.getUserId(), record.getRecordId(),
                request.getStockName(), request.getStockSymbol(), record.getDateOfPurchase(),
                request.getSellStockDate(), record.getPurchasePrice(), request.getsalePrice(), request.getShares());

        return soldRecord;
    }
}
