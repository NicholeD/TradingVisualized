package com.kenzie.appserver;

import com.kenzie.appserver.service.StockService;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.PurchasedStockResponse;
import com.kenzie.capstone.service.model.SellStockRequest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class ControllerIntegrationTests_sell {

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
    public void sellStock_ownedStockDecreases() throws Exception {
        //GIVEN
        String userId = mockNeat.strings().get();
        String symbol = "amzn";
        int sharesToSell = 5;

        PurchaseStockRequest purchaseStockRequest = new PurchaseStockRequest();
        purchaseStockRequest.setUserId(userId);
        purchaseStockRequest.setSymbol(symbol);
        purchaseStockRequest.setName(mockNeat.names().get());
        purchaseStockRequest.setPurchasePrice(32.00);
        purchaseStockRequest.setShares(10);
        purchaseStockRequest.setPurchaseDate(mockNeat.localDates().valStr());
        stockService.purchaseStock(purchaseStockRequest);

        SellStockRequest sellStockRequest = new SellStockRequest();
        sellStockRequest.setUserId(userId);
        sellStockRequest.setStockSymbol(symbol);
        sellStockRequest.setShares(sharesToSell);
        sellStockRequest.setSellStockDate(mockNeat.localDates().valStr());
        stockService.sellStock(sellStockRequest);

        //WHEN
        ResultActions actions = mvc.perform(get("/stocks/portfolio/{userId}", sellStockRequest.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<PurchasedStock> response = mapper.readValue(responseBody, new TypeReference<List<PurchasedStock>>() {});

        //THEN
        assertThat(response.get(0).getStock().getSymbol()).isEqualTo(symbol);
        assertThat(response.get(0).getStock().getQuantity()).isEqualTo(10 - sharesToSell);
    }

    @Test
    public void sellAllStock_stockIsRemoved() throws Exception {
        //GIVEN
        PurchaseStockRequest purchaseStockRequest = new PurchaseStockRequest();
        purchaseStockRequest.setUserId(mockNeat.strings().get());
        purchaseStockRequest.setSymbol("amzn");
        purchaseStockRequest.setName(mockNeat.names().get());
        purchaseStockRequest.setPurchasePrice(32.00);
        purchaseStockRequest.setShares(30);
        purchaseStockRequest.setPurchaseDate(mockNeat.localDates().valStr());

        stockService.purchaseStock(purchaseStockRequest);

        SellStockRequest sellStockRequest = new SellStockRequest();
        sellStockRequest.setUserId(purchaseStockRequest.getUserId());
        sellStockRequest.setStockSymbol(purchaseStockRequest.getSymbol());
        sellStockRequest.setShares(purchaseStockRequest.getShares());
        sellStockRequest.setsalePrice(100.00);
        sellStockRequest.setSellStockDate(mockNeat.localDates().valStr());

        stockService.sellStock(sellStockRequest);

        //WHEN
        ResultActions actions = mvc.perform(get("/stocks/portfolio/{userId}", sellStockRequest.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<PurchasedStock> response = mapper.readValue(responseBody, new TypeReference<List<PurchasedStock>>() {});

        //THEN
        assertThat(response).isEmpty();
    }

    //Add more tests for higher coverage
}