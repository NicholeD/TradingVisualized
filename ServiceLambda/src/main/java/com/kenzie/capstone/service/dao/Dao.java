package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.PurchasedStockRecord;
import com.kenzie.capstone.service.model.SellStockRequest;

import java.util.List;

public interface Dao {
    PurchasedStockRecord addPurchasedStock(PurchasedStockRecord stock);
    List<PurchasedStockRecord> findByUserId(String userId);

    PurchasedStockRecord sellStock(SellStockRequest request);

}
