package com.kenzie.appserver;

import com.kenzie.appserver.service.StockService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@IntegrationTest
public class ControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    StockService stockService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    public void cleanup (){

    }

    @Test
    public void getPurchasedStock_returnsPurchasedStock() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void purchaseStock_stockIsAddedToPortfolio() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void purchaseMoreStock_ownedStockIncreases() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void sellStock_ownedStockDecreases() {
        //GIVEN

        //WHEN

        //THEN
    }


    @Test
    public void userContacts_communicationReceived() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void userContacts_incompleteForm_throwsException() {
        //GIVEN

        //WHEN

        //THEN
    }

}
