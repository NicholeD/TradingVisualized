package com.kenzie.appserver.service.model.converter;

import com.kenzie.appserver.service.model.Fish;
import io.swagger.v3.core.util.Json;

import java.io.File;
import java.util.List;

public class ConversionTester {
    public static void main(String[] args) {
        Fish fish = new Fish("Bass", 2.5f, 10, 5.99, "true");
        Fish fish4 = new Fish("Bass", 2.5f, 11, 5.99, "true");
        Fish fish5 = new Fish("Trout", 1.5f, 5, 3.99, "false");
        List<Fish> fishList = List.of(fish, fish4, fish5);
        String json = JsonFishConverter.convertToJson(fishList);
        System.out.println(json);
        List<Fish> fish2 = JsonFishConverter.convertToFish(json);
        System.out.println(fish2);
        List<Fish> fish3 = JsonFishConverter.convertToFishFromFile(new File("exe\\TV_Data\\data.txt"));
        System.out.println(fish3);
        System.out.println(JsonFishConverter.convertToJson(fish3));
        JsonFishConverter.convertToJsonFile(fish3, new File("exe\\TV_Data\\data2.txt"));
    }
}
