package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PurchasedStockRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class PurchasedStockServiceTest {
    private PurchasedStockRepository purchasedStockRepository;

    @BeforeEach
    void setup() {
        purchasedStockRepository = mock(PurchasedStockRepository.class);
    }

    @Test
    void purchaseStock_stockIsPurchased() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void purchaseStock_emptyRequest_throwsIllegalArgumentException() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void purchaseStock_insufficientFunds_throwsInsufficientFundsException() {

    }
}
