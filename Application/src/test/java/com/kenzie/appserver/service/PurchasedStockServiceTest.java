package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.PurchasedStockRepository;
import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

public class PurchasedStockServiceTest {
    private PurchasedStockService purchasedStockService;
    private PurchasedStockRepository purchasedStockRepository;
    private FishRepository fishRepository;

    @BeforeEach
    void setup() {
        purchasedStockRepository = mock(PurchasedStockRepository.class);
        fishRepository = mock(FishRepository.class);
        purchasedStockService = new PurchasedStockService(purchasedStockRepository,
                fishRepository);
    }

    @Test
    public void purchaseStock_stockIsPurchased() {
        //GIVEN
        String userId = "userId";
        String symbol = "symbol";
        String name = "name";
        Stock stock = new Stock(symbol, name,
                10.0, 5, LocalDate.now().toString());
        PurchasedStock purchasedStock = new PurchasedStock(userId, stock, stock.getPurchaseDate());

        PurchaseStockRequest request = new PurchaseStockRequest(userId, symbol, name, stock.getPurchasePrice(),
                stock.getQuantity(), stock.getPurchaseDate(), stock.getPurchaseDate());

        //WHEN
       PurchasedStock purchasedStock1 = purchasedStockService.purchaseStock(request);

        //THEN
        Assertions.assertEquals(purchasedStock, purchasedStock1);
    }

    @Test
    public void purchaseStock_emptyRequest_throwsIllegalArgumentException() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void purchaseStock_insufficientFunds_throwsInsufficientFundsException() {

    }
}
