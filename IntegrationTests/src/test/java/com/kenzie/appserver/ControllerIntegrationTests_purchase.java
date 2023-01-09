package com.kenzie.appserver;

import com.kenzie.appserver.service.StockService;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import com.kenzie.capstone.service.model.PurchasedStockResponse;
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
public class ControllerIntegrationTests_purchase {

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
    public void getPurchasedStock_returnsPurchasedStock() throws Exception {
        //GIVEN
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.strings().get());
        request.setSymbol("amzn");
        request.setName(mockNeat.names().get());
        request.setPurchasePrice(mockNeat.doubles().get());
        request.setShares(mockNeat.ints().get());
        request.setPurchaseDate(mockNeat.localDates().valStr());

        PurchasedStockResponse response = stockService.purchaseStock(request);

        //WHEN
        ResultActions actions = mvc.perform(get("/stocks/portfolio/{userId}", response.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<PurchasedStock> purchasedStock = mapper.readValue(responseBody, new TypeReference<List<PurchasedStock>>() {});

        //THEN
        assertThat(purchasedStock.get(0).getStock().getUserId().equals(request.getUserId()));
    }

    @Test
    public void purchaseStock_stockIsAddedToPortfolio() throws Exception {
        //GIVEN
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.strings().get());
        request.setSymbol("amzn");
        request.setName(mockNeat.names().get());
        request.setPurchasePrice(mockNeat.doubles().get());
        request.setShares(mockNeat.ints().get());
        request.setPurchaseDate(mockNeat.localDates().valStr());
        stockService.purchaseStock(request);

        //WHEN
        ResultActions actions = mvc.perform(get("/stocks/portfolio/{userId}", request.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<PurchasedStock> purchasedStock = mapper.readValue(responseBody, new TypeReference<List<PurchasedStock>>() {});

        //THEN
        assertThat(purchasedStock).isNotEmpty();
        assertThat(purchasedStock.get(0).getStock().getSymbol()).isEqualTo(request.getSymbol());
        assertThat(purchasedStock.get(0).getStock().getUserId()).isEqualTo(request.getUserId());
    }

    @Test
    public void purchaseMoreStock_ownedStockIncreases() throws Exception {
        //GIVEN
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.strings().get());
        request.setSymbol("amzn");
        request.setName(mockNeat.names().get());
        request.setPurchasePrice(55.00);
        request.setShares(20);
        request.setPurchaseDate(mockNeat.localDates().valStr());

        stockService.purchaseStock(request);

        PurchaseStockRequest request2 = new PurchaseStockRequest();
        request.setUserId(request.getUserId());
        request.setSymbol(request.getSymbol());
        request.setName(request.getUserId());
        request.setPurchasePrice(45.00);
        request.setShares(5);
        request.setPurchaseDate(mockNeat.localDates().valStr());

        PurchasedStockResponse response = stockService.purchaseStock(request2);

        //WHEN
        ResultActions actions = mvc.perform(get("/stocks/portfolio/{userId}", response.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<PurchasedStock> purchasedStock = mapper.readValue(responseBody, new TypeReference<List<PurchasedStock>>() {});

        //THEN
        assertThat(purchasedStock.get(0).getUserId().equals(request.getUserId()));
        assertThat(purchasedStock.get(0).getStock().getQuantity() == (request.getShares() + request2.getShares()));
    }

    //Add more tests for higher coverage

}

