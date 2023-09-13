package com.kenzie.capstone.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.converter.JsonStringToPurchasedStockConverter;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JsonStringToPurchasedStockConverterTest {

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private ObjectMapper mapper = new ObjectMapper();
    private JsonStringToPurchasedStockConverter converter = new JsonStringToPurchasedStockConverter();

    @Test
    void convertTest(){
        PurchaseStockRequest request = new PurchaseStockRequest();
        request.setUserId(mockNeat.names().valStr());
        request.setName(mockNeat.names().valStr());
        request.setSymbol("TEST");
        request.setPurchaseDate(mockNeat.localDates().toString());
        request.setPurchasePrice(35.00);
        request.setShares(10);
        String body = null;
        try {
             body = mapper.writeValueAsString(request);
        }
        catch (Exception e){
            System.out.println("COULD NOT CONVERT TO STRING TEST");
        }
        assertNotNull(body);
        PurchaseStockRequest response = converter.convert(body);
    }
}
