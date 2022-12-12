package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.FishRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface FishRepository extends CrudRepository<FishRecord, String> {
}
