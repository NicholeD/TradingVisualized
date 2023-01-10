package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.StockAndFishConverter;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ConverterTests {
    public StockRepository stockRepository;
    public FishRepository fishRepository;
    public StockService stockService;
    private StockServiceClient stockServiceClient;
    private StockAndFishConverter converter;

    @BeforeEach
    void setup() {
        this.stockRepository = mock(StockRepository.class);
        this.fishRepository = mock(FishRepository.class);
        this.stockServiceClient = mock(StockServiceClient.class);
        this.stockService = new StockService(stockRepository, fishRepository, stockServiceClient);
        this.converter = new StockAndFishConverter(stockRepository, fishRepository, stockServiceClient);
    }

    @Test
    void convertStockToFish_convertsSuccessful() {
        //GIVEN
        Stock stock = new Stock("amzn",
                "name",
                10.00,
                11,
                LocalDate.now().toString());

        Fish fish = new Fish(stock.getSymbol(),
                stock.getName(),
                (float) stock.getPurchasePrice()*stock.getQuantity(),
                stock.getQuantity(),
                stock.getPurchasePrice(),
                "Alive");

        //WHEN
        Fish convertedFish = converter.stockToFish(stock);

        //THEN
        assertEquals(fish, convertedFish);
    }

    @Test
    void convertFishToStock_convertsSuccessful() {
        //GIVEN
        Fish fish = new Fish("amzn",
                "name",
                (float) 10*4,
                4,
                10.00,
                "Alive");

        Stock stock = new Stock(fish.getId(),
                fish.getName(),
                fish.getPrice(),
                LocalDate.now().toString());

        //WHEN
        Stock convertedStock = converter.fishToStock(fish);

        //THEN
        assertEquals(stock, convertedStock);
   }

   @Test
    void stockListToFishList_convertsSuccessful() {
        //GIVEN

       //WHEN

       //THEN


   }


}
