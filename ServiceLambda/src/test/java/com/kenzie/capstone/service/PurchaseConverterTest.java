package com.kenzie.capstone.service;

import com.kenzie.capstone.service.converter.PurchaseConverter;
import com.kenzie.capstone.service.model.*;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PurchaseConverterTest {
    private final MockNeat mockNeat = MockNeat.threadLocal();

    @Test
    void fromRequestToRecordTest (){
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().toString());
        request.setPurchasePrice(35.00);
        request.setShares(10);

        PurchasedStockRecord record = PurchaseConverter.fromRequestToRecord(request);

        assertEquals(record.getUserId(), request.getUserId());
        assertEquals(record.getName(), request.getName());
        assertEquals(record.getSymbol(), request.getSymbol());
        assertEquals(record.getDateOfPurchase(), request.getPurchaseDate());
        assertEquals(record.getPurchasePrice(), request.getPurchasePrice());
        assertEquals(record.getShares(), request.getShares());
    }

    @Test
    void fromRecordToResponse (){
        PurchasedStockRecord record = new PurchasedStockRecord();
        record.setUserId(mockNeat.names().valStr());
        record.setName(mockNeat.names().valStr());
        record.setSymbol("TEST");
        record.setDateOfPurchase(mockNeat.localDates().valStr());
        record.setPurchasePrice(35.00);
        record.setShares(10);

        PurchasedStockResponse response = PurchaseConverter.fromRecordToResponse(record);

        assertEquals(record.getUserId(), response.getUserId());
        assertEquals(record.getSymbol(), response.getStockSymbol());
        assertEquals(record.getDateOfPurchase(), response.getPurchaseDate());
        assertEquals(record.getPurchasePrice(), response.getPurchasePrice());
        assertEquals(record.getShares(), response.getShares());
    }

    @Test
    void fromRecordToPurchasedStock (){
        PurchasedStockRecord record = new PurchasedStockRecord();
        record.setUserId(mockNeat.names().valStr());
        record.setName(mockNeat.names().valStr());
        record.setSymbol("TEST");
        record.setDateOfPurchase(mockNeat.localDates().valStr());
        record.setPurchasePrice(35.00);
        record.setShares(10);

        PurchasedStock stock = PurchaseConverter.fromRecordToPurchasedStock(record);

        assertEquals(record.getUserId(), stock.getUserId());
        assertEquals(record.getSymbol(), stock.getStock().getSymbol());
        assertEquals(record.getDateOfPurchase(), stock.getStock().getPurchaseDate());
        assertEquals(record.getPurchasePrice(), stock.getStock().getPurchasePrice());
        assertEquals(record.getShares(), stock.getStock().getQuantity());
        assertEquals(record.getDateOfPurchase(), stock.getpurchasedDate());
        assertEquals(record.getName(), stock.getStock().getName());
    }

    @Test
    void fromRecordToSellStock (){
        PurchasedStockRecord record = new PurchasedStockRecord();
        record.setUserId(mockNeat.names().valStr());
        record.setName(mockNeat.names().valStr());
        record.setSymbol("TEST");
        record.setDateOfPurchase(mockNeat.localDates().valStr());
        record.setPurchasePrice(35.00);
        record.setShares(10);

        SellStockResponse response = PurchaseConverter.fromRecordToSellStock(record);

        assertEquals(record.getUserId(), response.getUserId());
        assertEquals(record.getSymbol(), response.getSymbol());
        assertEquals(record.getPurchasePrice(), response.getSalePrice());
        assertEquals(record.getShares(), response.getShares());
        assertEquals(record.getName(), response.getName());
        assertNotNull(response.getSellDate());
    }
}
