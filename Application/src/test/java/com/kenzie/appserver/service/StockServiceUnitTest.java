package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.repositories.model.SoldStockRecord;
import com.kenzie.appserver.service.model.Stock;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStockResponse;
import com.kenzie.capstone.service.model.SellStockRequest;
import com.kenzie.capstone.service.model.SellStockResponse;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockServiceUnitTest {

    private StockRepository stockRepository;
    private final MockNeat mockNeat = MockNeat.threadLocal();
    private FishRepository fishRepository;

    private StockServiceClient stockServiceClient;
    private StockService stockService;

    @BeforeEach
    void setup(){
        this.stockRepository = mock(StockRepository.class);
        this.fishRepository = mock(FishRepository.class);
        this.stockServiceClient = mock(StockServiceClient.class);
        this.stockService = new StockService(stockRepository, fishRepository, stockServiceClient);
        try {
            //too many consecutive calls to external api causes it to fail
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getStocksBySymboltest () {
        String symbol = "amzn";
        String name = "Amazon Inc.";
        StockResponse response = stockService.getStocksBySymbol(symbol);
        Map<String, String> metadata = response.getMetaData();
        Map<String, HashMap<String, String>> stocksByDay = response.getStocksByDay();
        System.out.println(metadata.toString());
        List<Stock> stocks = new ArrayList<>();

        for(Map.Entry<String, HashMap<String, String>> day : stocksByDay.entrySet()) {
            Stock stock = new Stock(symbol, name, Double.valueOf(day.getValue().get("4. close")), (day.getKey()));
            stocks.add(stock);
        }

        assertEquals(stocks.get(0).getSymbol(), symbol);
        assertEquals(stocks.get(0).getName(), name);
        assertNotNull(stocks.get(0).getPurchasePrice());
        assertNotNull(stocks.get(0).getPurchaseDate());
    }

    @Test
    void getStockNameBySymbol () {
        String symbol = "amzn";
        String name = "Amazon.com Inc";
        String response = stockService.getStockNameBySymbol(symbol);

        assertEquals(response, name);
    }


    @Test
    void findByUserId (){
        List<PurchasedStockRecord> stocks = new ArrayList<>();
            stocks.add(new PurchasedStockRecord("TEST",
                    mockNeat.names().valStr(), "amzn", LocalDate.now().toString(),  100.00, 10));

        when(stockRepository.findByUserId("TEST")).thenReturn(stocks);

        List<Stock> responseStocks = stockService.findByUserId("TEST");

        assertEquals(responseStocks.get(0).getUserId(),stocks.get(0).getUserId());
        assertEquals(responseStocks.get(0).getName(),stocks.get(0).getName());
        assertEquals(responseStocks.get(0).getSymbol(),stocks.get(0).getSymbol());
        assertEquals(responseStocks.get(0).getPurchaseDate(),stocks.get(0).getDateOfPurchase());
        assertEquals(responseStocks.get(0).getPurchasePrice(),stocks.get(0).getPurchasePrice());
        assertEquals(responseStocks.get(0).getQuantity(),stocks.get(0).getShares());

    }


    @Test
    void purchaseStockZeroSharesTestThrowsException() {
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().toString());
        request.setPurchasePrice(35.00);
        request.setShares(0);

        assertThrows(ResponseStatusException.class, () -> stockService.purchaseStock(request));
    }

    @Test
    void purchaseStockTest() {
        List<PurchasedStockRecord> stocks = new ArrayList<>();
        stocks.add(new PurchasedStockRecord(mockNeat.names().valStr(),
                mockNeat.names().valStr(), "TEST", LocalDate.now().toString(),
                100.00, 10));

        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(stocks.get(0).getUserId());
        request.setName(stocks.get(0).getName());
        request.setSymbol("TEST");
        request.setPurchaseDate(stocks.get(0).getDateOfPurchase());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        when(stockRepository.findByUserId(any())).thenReturn(stocks);
        when(stockServiceClient.addPurchasedStock(any())).thenReturn(new PurchasedStockResponse());
        when(stockRepository.save(any())).thenReturn(new Object());
        when(fishRepository.save(any())).thenReturn(new Object());

        PurchasedStockResponse response = stockService.purchaseStock(request);

        assertEquals(request.getUserId(), response.getUserId());
        assertEquals(request.getSymbol(), response.getStockSymbol());
        assertEquals(request.getPurchaseDate(), response.getPurchaseDate());
        assertEquals(request.getPurchasePrice(), response.getPurchasePrice());
        assertEquals(request.getShares(), response.getShares());
    }

    @Test
    void sellStockZeroSharesThrowsException() {
        SellStockRequest sellRequest = new SellStockRequest();
        sellRequest.setUserId(mockNeat.names().valStr());
        sellRequest.setStockName(mockNeat.names().valStr());
        sellRequest.setStockSymbol("TEST");
        sellRequest.setSellStockDate(mockNeat.localDates().valStr());
        sellRequest.setsalePrice(35.00);
        sellRequest.setShares(0);

        assertThrows(ResponseStatusException.class, () -> stockService.sellStock(sellRequest));

    }

    @Test
    void sellStockTest() {

        List<PurchasedStockRecord> stocks = new ArrayList<>();
        stocks.add(new PurchasedStockRecord(mockNeat.names().valStr(),
                        mockNeat.names().valStr(), "TEST", LocalDate.now().toString(),
                    100.00, 10));


        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().toString());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        when(stockRepository.findByUserId(any())).thenReturn(stocks);
        when(stockServiceClient.addPurchasedStock(any())).thenReturn(new PurchasedStockResponse());
        when(stockRepository.save(any())).thenReturn(new Object());
        when(fishRepository.save(any())).thenReturn(new Object());

        PurchasedStockResponse response = stockService.purchaseStock(request);

        SellStockRequest sellRequest = new SellStockRequest();
        sellRequest.setUserId(mockNeat.names().valStr());
        sellRequest.setStockName(mockNeat.names().valStr());
        sellRequest.setStockSymbol("TEST");
        sellRequest.setSellStockDate(mockNeat.localDates().valStr());
        sellRequest.setsalePrice(35.00);
        sellRequest.setShares(10);

        SellStockResponse sellResponse = new SellStockResponse();
        sellResponse.setUserId(sellRequest.getUserId());
        sellResponse.setName(sellRequest.getStockName());
        sellResponse.setSymbol(sellRequest.getStockSymbol());
        sellResponse.setShares(sellRequest.getShares());
        sellResponse.setSellDate(LocalDate.now().toString());
        sellResponse.setSalePrice(sellRequest.getsalePrice());


        when(stockServiceClient.sellStock(sellRequest)).thenReturn(sellResponse);

        SoldStockRecord record = stockService.sellStock(sellRequest);

        assertEquals(record.getUserId(), sellResponse.getUserId());
        assertEquals(record.getStockName(), sellResponse.getName());
        assertEquals(record.getStockSymbol(), sellResponse.getSymbol());
        assertEquals(record.getShares(), sellResponse.getShares());
        assertEquals(record.getDateOfSale(), sellResponse.getSellDate());
        assertEquals(record.getSaleStockPrice(), sellResponse.getSalePrice());
    }

}
