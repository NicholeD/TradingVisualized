package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PurchasedStockRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface PurchasedStockRepository extends CrudRepository<PurchasedStockRecord, String> {
    List<PurchasedStockRecord> findBySymbol(String symbol);
}