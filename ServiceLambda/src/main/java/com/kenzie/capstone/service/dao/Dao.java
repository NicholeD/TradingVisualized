package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.PurchasedStockRecord;

import java.util.List;

public interface Dao {
    PurchasedStockRecord addPurchasedStock(PurchasedStockRecord stock);
    List<PurchasedStockRecord> findByUserId(String userId);

}
