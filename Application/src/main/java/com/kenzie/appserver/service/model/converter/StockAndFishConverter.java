package com.kenzie.appserver.service.model.converter;

import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.Stock;

import java.time.LocalDateTime;

public class StockAndFishConverter {


    public static Fish stockToFish(Stock stock){
        Fish fish = new Fish();
        fish.setName(stock.getName()); //not sure if fish name should be stock name or stock symbol
        fish.setPrice(stock.getPurchasePrice());
        fish.setQuantity(stock.getQuantity());
//        fish.setSize();  this is going to be either a ratio of the price and quantity of stock or not sure
        fish.setStatus(true);
        return fish;
    }

    public static Stock fishToStock(Fish fish){
        String symbol = ""; //some way to get a symbol are change stock class to add it down the line
        String dayOfPurchase = ""; //dont know if we want this to be accurate
        Stock stock = new Stock(symbol, fish.getName(), fish.getPrice(), LocalDateTime.now().toString());
        return stock;
    }




}
