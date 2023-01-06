package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.FishCreateRequest;
import com.kenzie.appserver.controller.model.FishResponse;
import com.kenzie.appserver.service.FishService;
import com.kenzie.appserver.service.model.Fish;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fish")
public class FishController {

    private FishService fishService;

    FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<FishResponse> getFish(@PathVariable("name") String name) {
        Fish fish = fishService.findByName(name);
        if (fish == null) {
            return ResponseEntity.notFound().build();
        }

        FishResponse fishResponse = new FishResponse();
        fishResponse.setName(fish.getName());
        fishResponse.setSize(fish.getSize());
        fishResponse.setQuantity(fish.getQuantity());
        fishResponse.setPrice(fish.getPrice());
        fishResponse.setStatus(fish.getStatus());
        return ResponseEntity.ok(fishResponse);
    }

    @PostMapping
    public ResponseEntity<FishResponse> addNewFish(@RequestBody FishCreateRequest fishCreateRequest) {
        Fish newFish = fishService.addNewFish(fishCreateRequest.getName(), fishCreateRequest.getSize(), fishCreateRequest.getQuantity(), fishCreateRequest.getPrice(), fishCreateRequest.isStatus());

        FishResponse fishResponse = new FishResponse();
        fishResponse.setName(newFish.getName());
        fishResponse.setSize(newFish.getSize());
        fishResponse.setQuantity(newFish.getQuantity());
        fishResponse.setPrice(newFish.getPrice());
        fishResponse.setStatus(newFish.getStatus());

        return ResponseEntity.ok(fishResponse);
    }
}
