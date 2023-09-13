package com.kenzie.capstone.service;

import com.kenzie.capstone.service.caching.CachingStockDao;
import com.kenzie.capstone.service.model.*;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StockServiceTest {

    private CachingStockDao dao;
    private StockService stockService;
    private final MockNeat mockNeat = MockNeat.threadLocal();

    @BeforeEach
    void setup() {
        this.dao = mock(CachingStockDao.class);
        this.stockService = new StockService(dao);
    }

    @Test
    void setPurchasedStockTest() {
        ArgumentCaptor<PurchasedStockRecord> recordCaptor = ArgumentCaptor.forClass(PurchasedStockRecord.class);

        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().valStr());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        PurchasedStockResponse response = stockService.setPurchasedStock(request);

        verify(dao, times(1)).addPurchasedStock(recordCaptor.capture());

        assertNotNull(recordCaptor.getValue(), "RECORD SHOULD NOT BE NULL");

        PurchasedStockRecord record = recordCaptor.getValue();

        assertEquals(record.getUserId(), request.getUserId());
        assertEquals(record.getName(), request.getName());
        assertEquals(record.getSymbol(), request.getSymbol());
        assertEquals(record.getDateOfPurchase(), request.getPurchaseDate());
        assertEquals(record.getPurchasePrice(), request.getPurchasePrice());
        assertEquals(record.getShares(), request.getShares());
    }

    @Test
    void getPurchasedStocksTest() {
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().valStr());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        PurchasedStockResponse response = stockService.setPurchasedStock(request);
        assertNotNull(response, "RECORD SHOULD NOT BE NULL");

        String userId = request.getUserId();
        List<PurchasedStockRecord> stocks = new ArrayList<>();
        stocks.add(new PurchasedStockRecord(request.getUserId(), request.getName(), request.getSymbol(), request.getPurchaseDate(),request.getPurchasePrice(),request.getShares()));
        when(dao.findByUserId(any())).thenReturn(stocks);

        List<PurchasedStock> stockList = stockService.getPurchasedStocks(userId);
        assertTrue(!stockList.isEmpty());

        assertEquals(stockList.get(0).getUserId(), request.getUserId());
        assertEquals(stockList.get(0).getStock().getName(), request.getName());
        assertEquals(stockList.get(0).getStock().getSymbol(), request.getSymbol());
        assertEquals(stockList.get(0).getStock().getPurchaseDate(), request.getPurchaseDate());
        assertEquals(stockList.get(0).getStock().getPurchasePrice(), request.getPurchasePrice());
        assertEquals(stockList.get(0).getStock().getQuantity(), request.getShares());
    }

    @Test
    void sellPurchasedStocksTest() {
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().valStr());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        PurchasedStockResponse response = stockService.setPurchasedStock(request);
        assertNotNull(response, "PURCHASERESPONSE SHOULD NOT BE NULL");

        SellStockRequest sellRequest = new SellStockRequest();
        sellRequest.setUserId(request.getUserId());
        sellRequest.setStockName(request.getName());
        sellRequest.setStockSymbol("TEST");
        sellRequest.setSellStockDate(mockNeat.localDates().valStr());
        sellRequest.setsalePrice(35.00);
        sellRequest.setShares(10);

        when(dao.sellStock(sellRequest)).thenReturn(new PurchasedStockRecord(request.getUserId(), request.getName(), request.getSymbol(), request.getPurchaseDate(),request.getPurchasePrice(),request.getShares()));

        SellStockResponse sellResponse = stockService.sellStock(sellRequest);
        assertNotNull(response, "SellResponse SHOULD NOT BE NULL");

        List<PurchasedStockRecord> stocks = new ArrayList<>();
        stocks.add(new PurchasedStockRecord(request.getUserId(), request.getName(), request.getSymbol(), request.getPurchaseDate(),request.getPurchasePrice(),request.getShares()));
        when(dao.findByUserId(any())).thenReturn(stocks);

        String userId = sellRequest.getUserId();
        List<PurchasedStock> stockList = stockService.getPurchasedStocks(userId);

        assertTrue(!stockList.isEmpty());
    }
}
