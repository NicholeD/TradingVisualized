package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.StockRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan

public interface StockRepository extends CrudRepository<StockRecord, String> {
    StockRecord findStockBySymbol(String symbol);
    List<StockRecord> findByUserId(String userId);
}
