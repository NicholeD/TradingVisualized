package com.kenzie.appserver.service.model.converter;

import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.Stock;
import com.kenzie.capstone.service.client.StockServiceClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StockAndFishConverter {
    public StockRepository stockRepository;
    public FishRepository fishRepository;

    public static StockService stockService;

    private StockServiceClient stockServiceClient;

    public StockAndFishConverter(StockRepository stockRepository, FishRepository fishRepository, StockServiceClient stockServiceClient) {
        this.stockRepository = stockRepository;
        this.fishRepository = fishRepository;
        this.stockServiceClient = stockServiceClient;
        this.stockService = new StockService(stockRepository, fishRepository, stockServiceClient);
    }

    public static Fish stockToFish(Stock stock){
        Fish fish = new Fish();
        fish.setId(stock.getSymbol());
        fish.setName(stock.getName()); //not sure if fish name should be stock name or stock symbol
        fish.setPrice(stock.getPurchasePrice());
        fish.setQuantity(stock.getQuantity());
        fish.setSize((float) stock.getPurchasePrice()*stock.getQuantity());
        fish.setStatus("Alive"); // might need to change this per scoots last message
        return fish;
    }

    public static Stock fishToStock(Fish fish){
        String dayOfPurchase = ""; //dont know if we want this to be accurate
        String name = stockService.getStockNameBySymbol(fish.getName());
        Stock stock = new Stock(fish.getName(), name, fish.getPrice(), LocalDateTime.now().toString());
        return stock;
    }

    public static List<Fish> stockListToFishList(List<Stock> stockList){
        return stockList.stream()
                .map(s -> new Fish(s.getName(),(float)(s.getPurchasePrice()*s.getQuantity()),s.getQuantity(), s.getPurchasePrice(), "Active"))
                .collect(Collectors.toList());
    }

    public static List<Stock> fishListToStockList(List<Fish> fishList){
        return fishList.stream()
                .map(f -> {
                    String name = stockService.getStockNameBySymbol(f.getName());
                    System.out.println("StockFishConverterList could be a lot of calls to external api.");
                    return new Stock(f.getName(), name, f.getPrice(), LocalDateTime.now().toString());
                })
                .collect(Collectors.toList());
    }

    public static List<Fish> purchasedStockToFishList (List<PurchasedStock> stockList){
        return stockList.stream()
                .map(r ->{
                    Stock stock = r.getStock();
                    return new Fish(stock.getSymbol(), stock.getName(), (float) (stock.getQuantity()*stock.getPurchasePrice()), stock.getQuantity(),stock.getPurchasePrice(), "Alive");
                }).collect(Collectors.toList());
    }
    public static List<Fish> purchasedStockListConvertToFishList(List<PurchasedStock> purcasedStockList) {
         return purcasedStockList.stream()
                .map(f -> {
                    Stock current = f.getStock();
                    Fish fish = stockToFish(current);
                    return fish;
                }).collect(Collectors.toList());
    }

    public static List<PurchaseStockRequest> fishListToPurchasedStockRecord(List<Fish> fishList) {
        return fishList.stream()
                .map(f -> {
                    PurchaseStockRequest request = new PurchaseStockRequest();
                    if(f.getStatus().equals("Alive")) {
                        request = new PurchaseStockRequest("userId", f.getId(), f.getName(), f.getPrice(), (int)Math.round(f.getQuantity()), LocalDate.now().toString());
                    }
                    return request;
                })
                .filter(purchaseStockRequest -> purchaseStockRequest.getName() != null)
                .collect(Collectors.toList());
    }
}
