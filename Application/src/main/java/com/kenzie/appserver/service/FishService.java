package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FishRepository;
import com.kenzie.appserver.repositories.model.FishRecord;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.stereotype.Service;

@Service
public class FishService {
    private FishRepository fishRepository;
    private LambdaServiceClient lambdaServiceClient;
    //This is hopefully useful... but I don't exactly know what it is doing atm...

    public FishService(FishRepository fishRepository, LambdaServiceClient lambdaServiceClient) {
        this.fishRepository = fishRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public Fish findByName(String name) {
        FishRecord fishRecord = fishRepository.findById(name).orElse(null);
        Fish fish = new Fish(fishRecord.getName(), fishRecord.getSize(), fishRecord.getQuantity(), fishRecord.getPrice(), fishRecord.isStatus());
        return fish;
    }

    public Fish addNewFish(String name, float size, double quantity, double price, String status) {
        FishRecord fishRecord = new FishRecord();
        fishRecord.setName(name);
        fishRecord.setSize(size);
        fishRecord.setQuantity(quantity);
        fishRecord.setPrice(price);
        fishRecord.setStatus(status);
        fishRepository.save(fishRecord);
        return new Fish(name, size, quantity, price, status);
    }

    public Fish addNewFish(Fish fish) {
        FishRecord fishRecord = new FishRecord();
        fishRecord.setName(fish.getName());
        fishRecord.setSize(fish.getSize());
        fishRecord.setQuantity(fish.getQuantity());
        fishRecord.setPrice(fish.getPrice());
        fishRecord.setStatus(fish.isStatus());
        fishRepository.save(fishRecord);
        return fish;
    }
}
