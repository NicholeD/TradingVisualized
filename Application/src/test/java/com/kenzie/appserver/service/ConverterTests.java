package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.JsonFishConverter;
import com.kenzie.appserver.service.model.converter.StockAndFishConverter;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class ConverterTests {
    public StockRepository stockRepository;
    public FishRepository fishRepository;
    public StockService stockService;
    private StockServiceClient stockServiceClient;
    private StockAndFishConverter converter;
    private JsonFishConverter jsonFishConverter;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.jsonFishConverter = mock(JsonFishConverter.class);
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
        assertEquals(fish.getId(), convertedFish.getId());
        assertEquals(fish.getQuantity(), convertedFish.getQuantity());
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
        assertEquals(stock.getUserId(), convertedStock.getUserId());
   }

   @Test
    void stockListToFishList_convertsSuccessful() {
        //GIVEN
       List<Stock> stockList = new ArrayList<>();
       Stock stock1 = new Stock("amzn", "name1", 10.00, 3, LocalDate.now().toString());
       stockList.add(stock1);

       List<Fish> fishList = new ArrayList<>();
       Fish fish = converter.stockToFish(stock1);
       fishList.add(fish);

       //WHEN
        List<Fish> convertedList = converter.stockListToFishList(stockList);

       //THEN
       assertEquals(fishList.size(), convertedList.size());
       assertEquals(fishList.get(0).getName(), convertedList.get(0).getName());
   }

    @Test
    void fishListToStockList_convertsSuccessful() {
        //GIVEN
        List<Fish> fishList = new ArrayList<>();
        Fish fish = new Fish("amzn","name",(float) 10*4, 4,10.00,"Alive");
        fishList.add(fish);

        List<Stock> stockList = new ArrayList<>();
        Stock stock = converter.fishToStock(fish);
        stockList.add(stock);

        //WHEN
        List<Stock> stocks = converter.fishListToStockList(fishList);

        //THEN
        assertEquals(stockList.size(), stocks.size());
        assertEquals(stockList.get(0).getUserId(), stocks.get(0).getUserId());
        assertEquals(stockList.get(0).getName(), stocks.get(0).getName());
    }

    @Test
    void purchasedStockToFishList_convertsSuccessful() {
        //GIVEN
        List<PurchasedStock> purchasedStocks = new ArrayList<>();
        Stock stock = new Stock("amzn",
                "name",
                10.00,
                11,
                LocalDate.now().toString());
        PurchasedStock purchasedStock = new PurchasedStock();
        purchasedStock.setStock(stock);
        purchasedStock.setUser("userId");
        purchasedStock.setpurchasedDate(LocalDate.now().toString());
        purchasedStocks.add(purchasedStock);

        List<Fish> fishList = new ArrayList<>();
        fishList.add(StockAndFishConverter.stockToFish(stock));

        //WHEN
        List<Fish> convertedList = StockAndFishConverter.purchasedStockToFishList(purchasedStocks);

        //THEN
        assertEquals(fishList.size(), convertedList.size());
        assertEquals(fishList.get(0).getId(), convertedList.get(0).getId());
        assertEquals(fishList.get(0).getQuantity(), convertedList.get(0).getQuantity());
    }

    @Test
    void purchasedStockListConvertToFishList_convertsSuccessful() {
        //GIVEN
        List<PurchasedStock> purchasedStocks = new ArrayList<>();
        Stock stock = new Stock("amzn",
                "name",
                10.00,
                11,
                LocalDate.now().toString());
        PurchasedStock purchasedStock = new PurchasedStock();
        purchasedStock.setStock(stock);
        purchasedStock.setUser("userId");
        purchasedStock.setpurchasedDate(LocalDate.now().toString());
        purchasedStocks.add(purchasedStock);

        List<Fish> fishList = new ArrayList<>();
        fishList.add(StockAndFishConverter.stockToFish(stock));

        //WHEN
        List<Fish> convertedList = StockAndFishConverter.purchasedStockListConvertToFishList(purchasedStocks);

        //THEN
        assertEquals(fishList.size(), convertedList.size());
        assertEquals(fishList.get(0).getId(), convertedList.get(0).getId());
        assertEquals(fishList.get(0).getQuantity(), convertedList.get(0).getQuantity());
    }

    @Test
    void fishListToPurchasedStockRecord_convertsSuccessful() {
        //GIVEN
        List<Fish> fishList = new ArrayList<>();
        Fish fish = new Fish("amzn","name", (float) 10*4,4,10.00,"Alive");
        fishList.add(fish);

        List<PurchaseStockRequest> purchaseStockRequests = new ArrayList<>();
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setName(fish.getName());
        request.setSymbol(fish.getId());
        request.setShares(4);
        request.setPurchasePrice(fish.getPrice());
        request.setPurchaseDate(LocalDate.now().toString());
        purchaseStockRequests.add(request);
        purchaseStockRequests.stream()
                .map(r -> {
                    if (fish.getStatus().equals("Alive")) {
                        r = new PurchaseStockRequest(r.getUserId(), r.getSymbol(), r.getName(), r.getPurchasePrice(), r.getShares(), r.getPurchaseDate());
                    }
                    return r;
                })
                .filter(purchaseStockRequest -> purchaseStockRequest.getName() != null)
                .collect(Collectors.toList());

        //WHEN
        List<PurchaseStockRequest> convertedList = StockAndFishConverter.fishListToPurchasedStockRecord(fishList);

        //THEN
        assertEquals(purchaseStockRequests.size(), convertedList.size());
        assertEquals(purchaseStockRequests.get(0).getSymbol(), convertedList.get(0).getSymbol());
    }

    @Test
    void convertToJson_withNullFish() {
        //GIVEN
        Fish fish = null;

        //WHEN
        String json = jsonFishConverter.convertToJson(fish);

        //THEN
        assertNull(json);
    }

    @Test
    void convertToFish_withNullJson() {
        //GIVEN
        String json = null;

        //WHEN
        List<Fish> fish = jsonFishConverter.convertToFish(json);

        //THEN
        assertNull(fish);
    }

    @Test
    void convertToJson_convertSuccessful() throws JsonProcessingException {
        //GIVEN
        List<Fish> fishList = new ArrayList<>();
        Fish fish = new Fish("amzn","name",(float) 10*4, 4,10.00,"Alive");
        fishList.add(fish);

        String json = mapper.writeValueAsString(fishList);

        //WHEN
        String convertedJson = jsonFishConverter.convertToJson(fishList);

        //THEN
        assertEquals(json, convertedJson);
    }
}
