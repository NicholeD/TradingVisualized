package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import com.kenzie.appserver.repositories.model.StockRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan

public interface StockRepository extends CrudRepository<PurchasedStockRecord, String> {
    PurchasedStockRecord findStockBySymbol(String symbol);
    List<PurchasedStockRecord> findByUserId(String userId);
}
