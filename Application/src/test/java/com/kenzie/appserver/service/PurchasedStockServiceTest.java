package com.kenzie.appserver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class PurchasedStockServiceTest {


    @BeforeEach
    void setup() {

    }

    @Test
    public void purchaseStock_stockIsPurchased() {
        //GIVEN

        //WHEN

        //THEN
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
