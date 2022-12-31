package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.StockRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class SoldStockServiceTest {
    private StockRepository stockRepository;

    @BeforeEach
    void setup() { stockRepository = mock(StockRepository.class); }

    @Test
    void sellStock_purchasedStockIsUpdated() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void sellMoreStockThanOwn_throwsException() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void sellStock_allShares_purchasedStockIsDeleted() {
        //GIVEN

        //WHEN

        //THEN
    }
}
