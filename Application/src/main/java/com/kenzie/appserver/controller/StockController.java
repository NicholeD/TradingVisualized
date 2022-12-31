package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.repositories.model.StockRecord;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.SoldStock;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private StockService stockService;

    //TODO - should this be lambda client?
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    public StockController(StockService stockService) { this.stockService = stockService; }

    @GetMapping("/{symbol}")
    public ResponseEntity<SearchStockResponse> getStocksBySymbol(@PathVariable("symbol") String symbol) {
        StockResponse stockResponse = stockService.getStocksBySymbol(symbol);

        //If there is no stock, return 204
        if (stockResponse == null) {
            return ResponseEntity.notFound().build();
        }
        //Otherwise, convert into searchStockResponse and return it.
        SearchStockResponse searchStockResponse = createSearchStockResponse(stockResponse, symbol);

        return ResponseEntity.ok(searchStockResponse);
    }

    @PostMapping
    public ResponseEntity<PurchasedStockResponse> purchaseStock(
            @RequestBody PurchaseStockRequest purchasedStockRequest) throws InsufficientResourcesException {
        String name = stockService.getStockNameBySymbol(purchasedStockRequest.getStockSymbol());

        StockRecord stock = new StockRecord();
        stock.setUserId(purchasedStockRequest.getUserId());
        stock.setSymbol(purchasedStockRequest.getStockSymbol());
        stock.setName(purchasedStockRequest.getStockName());
        stock.setPurchasePrice(purchasedStockRequest.getPurchasePrice());
        stock.setQuantity(purchasedStockRequest.getShares());
        stock.setPurchaseDate(purchasedStockRequest.getPurchaseDate());

        PurchasedStockResponse purchasedStockResponse = new PurchasedStockResponse();
        purchasedStockResponse.setUserId(purchasedStockRequest.getUserId());
        purchasedStockResponse.setStockSymbol(stock.getSymbol());
        purchasedStockResponse.setUserId(name);
        purchasedStockResponse.setPurchasePrice(stock.getPurchasePrice());
        purchasedStockResponse.setShares(stock.getQuantity());
        purchasedStockResponse.setPurchasePrice(stock.getPurchasePrice()*stock.getQuantity());
        purchasedStockResponse.setPurchaseDate(stock.getPurchaseDate());
        purchasedStockResponse.setOrderDate(purchasedStockRequest.getOrderDate());

        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("id", new AttributeValue(purchasedStockRequest.getUserId()));
        keyToGet.put("symbol", new AttributeValue(purchasedStockRequest.getStockSymbol()));
        keyToGet.put("quantity", new AttributeValue().withN(Integer.toString(purchasedStockRequest.getShares())));
        keyToGet.put("purchaseDate", new AttributeValue(purchasedStockRequest.getPurchaseDate()));
        keyToGet.put("purchasePrice", new AttributeValue().withN(Double.toString(purchasedStockRequest.getPurchasePrice())));
        client.putItem("Portfolio", keyToGet);

        return ResponseEntity.created(URI.create("/purchasedstocks/" + purchasedStockResponse.getUserId())).body(purchasedStockResponse);
    }

    @PostMapping
    public ResponseEntity<SellStockResponse> sellStock(@RequestBody SellStockRequest sellStockRequest) {

        SoldStockRecord soldStockRecord = stockService.sellStock(sellStockRequest);

        Stock stock = recordToStock(soldStockRecord);

        SoldStock soldStock = new SoldStock(sellStockRequest.getUserId(),
                sellStockRequest.getRecordId(), sellStockRequest.getStockSymbol(),
                sellStockRequest.getStockName(), sellStockRequest.getsalePrice(),
                sellStockRequest.getShares(), LocalDate.now().toString());

        SellStockResponse response = createSellStockResponse(soldStock);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScanResult> getPortfolioByUserId(@PathVariable("userId") String userId) {
        ScanResult result = client.scan("Portfolio", null, null);
        return ResponseEntity.ok(result);
    }

    /** Helpers */
    private ZonedDateTime convertDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(string, formatter);

        return date.atStartOfDay(ZoneId.systemDefault());
    }

    private SearchStockResponse createSearchStockResponse(StockResponse stockResponse, String symbol) {
        List<Stock> stocks = new ArrayList<>();
        String name = stockService.getStockNameBySymbol(symbol);

        Map<String, HashMap<String, String>> stocksByDay = stockResponse.getStocksByDay();

        for(Map.Entry<String, HashMap<String, String>> day : stocksByDay.entrySet()) {
            Stock stock = new Stock(symbol, name, Double.valueOf(day.getValue().get("4. close")), (day.getKey()));
            stocks.add(stock);
        }

        SearchStockResponse searchStockResponse = new SearchStockResponse();
        searchStockResponse.setSymbol(symbol);
        searchStockResponse.setName(name);
        searchStockResponse.setStocks(stocks.stream().limit(30).collect(Collectors.toList()));

        return searchStockResponse;
    }

    private Stock recordToStock(SoldStockRecord record) {
        return new Stock(record.getStockSymbol(), record.getStockName(),
                record.getSaleStockPrice(), record.getShares(), record.getDateOfSale());
    }

    private SellStockResponse createSellStockResponse(SoldStock soldStock) {
        SellStockResponse sellStockResponse = new SellStockResponse();
        sellStockResponse.setUserId(soldStock.getUserId());
        sellStockResponse.setRecordID(soldStock.getRecordId());
        sellStockResponse.setStockSymbol(soldStock.getSymbol());
        sellStockResponse.setStockName(soldStock.getName());
        sellStockResponse.setSalePrice(soldStock.getSoldPrice());
        sellStockResponse.setShares(soldStock.getQuantity());
        sellStockResponse.setSellStockDate(soldStock.getSoldDate());

        return sellStockResponse;
    }
}
