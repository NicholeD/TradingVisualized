package com.kenzie.capstone.service.caching;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kenzie.capstone.service.dao.Dao;
import com.kenzie.capstone.service.dao.StockDao;
import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.SellStockRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CachingStockDao implements Dao {
    private static final int STOCK_READ_TTL = 60 * 60;
    private static final String STOCK_KEY = "StockKey::%s";
    private final CacheClient cacheClient;
    private final StockDao stockDao;
    private final Gson gson;

    @Inject
    public CachingStockDao(CacheClient cacheClient, StockDao stockDao) {
        this.cacheClient = cacheClient;
        this.stockDao = stockDao;

        this.gson = new GsonBuilder().registerTypeAdapter(
                ZonedDateTime.class,
                new TypeAdapter<ZonedDateTime>() {
                    @Override
                    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                        out.value(value.toString());
                    }
                    @Override
                    public ZonedDateTime read(JsonReader in) throws IOException {
                        return ZonedDateTime.parse(in.nextString());
                    }
                }
        ).enableComplexMapKeySerialization().create();
    }

    @Override
    public PurchasedStockRecord addPurchasedStock(PurchasedStockRecord record) {
        // Invalidate
        cacheClient.invalidate(String.format(STOCK_KEY, record.getName()));
        System.out.println("IN CACHINGSTOCKDAO" + record);
        // Add referral to database
        return stockDao.addPurchasedStock(record);
    }

    @Override
    public PurchasedStockRecord sellStock(SellStockRequest request) {
        cacheClient.invalidate(String.format(STOCK_KEY, request.getStockName()));
        return stockDao.sellStock(request);
    }

    @Override
    public List<PurchasedStockRecord> findByUserId(String userId) {
        // Look up data in cache
        List<PurchasedStockRecord> purchasedRecords = new ArrayList<>();

        cacheClient.getValue(String.format(STOCK_KEY, userId)).ifPresentOrElse(string -> purchasedRecords.addAll(fromJson(string)),
                () -> purchasedRecords.addAll(addToCache(stockDao.findByUserId(userId), userId)));

        return purchasedRecords;
    }

    private List<PurchasedStockRecord> fromJson(String json) {
        return gson.fromJson(json, new TypeToken<ArrayList<PurchasedStockRecord>>() { }.getType());
    }

    private List<PurchasedStockRecord> addToCache(List<PurchasedStockRecord> records, String referrerId) {
        cacheClient.setValue(String.format(STOCK_KEY, referrerId),
                STOCK_READ_TTL, gson.toJson(records)
        );
        return records;
    }

}
