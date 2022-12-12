package com.kenzie.appserver.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PurchasedStockRepository extends CrudRepository<PurchasedStockRecord, String> {
}