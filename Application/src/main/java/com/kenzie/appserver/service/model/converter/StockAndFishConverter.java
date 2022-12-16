package com.kenzie.appserver.service.model.converter;

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
        fish.setStatus(true); // might need to change this per scoots last message
        return fish;
    }

    public static Stock fishToStock(Fish fish){
        String symbol = ""; //some way to get a symbol are change stock class to add it down the line
        String dayOfPurchase = ""; //dont know if we want this to be accurate
        Stock stock = new Stock(symbol, fish.getName(), fish.getPrice(), LocalDateTime.now().toString());
        return stock;
    }

    public static List<Fish> stockListToFishList(List<Stock> stockList){
        return stockList.stream()
                .map(s -> new Fish(s.getName(),(float)(s.getPurchasePrice()*s.getQuantity()),s.getQuantity(), s.getPurchasePrice(), true))
                .collect(Collectors.toList());
    }

    public static List<Stock> fishListToStockList(List<Fish> fishList){
        return fishList.stream()
                .map(f -> {
                    String symbol = ""; // watch out for using this converter, symbol will be blank until ask about it
                    System.out.println("symbol will be blank until fixed fishListToStockList converter");
                    return new Stock(symbol, f.getName(), f.getPrice(), LocalDateTime.now().toString());
                })
                .collect(Collectors.toList());
    }



}
