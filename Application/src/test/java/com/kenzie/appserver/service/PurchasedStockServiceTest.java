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
