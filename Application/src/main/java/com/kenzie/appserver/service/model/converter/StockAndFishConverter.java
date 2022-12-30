package com.kenzie.appserver.service.model.converter;

import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.Stock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StockAndFishConverter {


    public static Fish stockToFish(Stock stock){
        Fish fish = new Fish();
        fish.setName(stock.getName()); //not sure if fish name should be stock name or stock symbol
        fish.setPrice(stock.getPurchasePrice());
        fish.setQuantity(stock.getQuantity());
        fish.setSize((float) stock.getPurchasePrice()*stock.getQuantity());
        fish.setStatus("Active"); // might need to change this per scoots last message
        return fish;
    }

    public static Stock fishToStock(Fish fish){
        StockService stockService = new StockService();
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
        StockService stockService = new StockService();
        return fishList.stream()
                .map(f -> {
                    String name = stockService.getStockNameBySymbol(f.getName());
                    System.out.println("StockFishConverterList could be a lot of calls to external api.");
                    return new Stock(f.getName(), name, f.getPrice(), LocalDateTime.now().toString());
                })
                .collect(Collectors.toList());
    }

}
