package com.kenzie.appserver.service.model.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.service.model.Fish;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFishConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String convertToJson(Fish fish) {
        if(fish == null) {
            return null;
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(fish);
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the Mapper");
        }
        return json;
    }

    public static List<Fish> convertToFish(String json) {
        if(json == null) {
            return null;
        }
        List<String> substrings = jsonStringParser(json);
        List<Fish> fishList = new ArrayList<>();
        try {
            for(String s : substrings) {
                fishList.add(mapper.readValue(s, Fish.class));
            }
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the Mapper");
            e.printStackTrace();
        }
        return fishList;
    }

    public static List<Fish> convertToFishFromFile(File file) {
        if(file == null) {
            return null;
        }
        List<Fish> fishList = new ArrayList<>();
        List<String> substrings = fileStringParser(file);
        try {
            for(String s : substrings) {
                fishList.add(mapper.readValue(s, Fish.class));
            }
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the Mapper");
        }
        return fishList;
    }

    public static String convertToJson(List<Fish> fishList) {
        if(fishList == null) {
            return null;
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(fishList);
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the Mapper in the convertToJson method");
        }
        return json;
    }

    public static void convertToJsonFile(List<Fish> fishList, File file) {
        if(fishList == null || file == null) {
            return;
        }
        try {
            mapper.writeValue(file, fishList);
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the Mapper During Conversion to File");
        }
    }

    //Helper method to parse the JSON string into a list of strings
    private static List<String> jsonStringParser(String json) {
        List<String> substrings = new ArrayList<>();
        int start = 0;
        int end = 0;
        for(int i = 0; i < json.length(); i++) {
            if(json.charAt(i) == '{') {
                start = i;
            }
            if(json.charAt(i) == '}') {
                end = i;
                substrings.add(json.substring(start, end + 1));
            }
        }
        return substrings;
    }

    //Helper method to parse the JSON file into a list of strings
    private static List<String> fileStringParser(File file) {
        List<String> substrings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
            String line = reader.readLine();
            int start = 0;
            int end = 0;
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '{') {
                    start = i;
                }
                if(line.charAt(i) == '}') {
                    end = i;
                    substrings.add(line.substring(start, end + 1));
                }
            }
        } catch (IOException e) {
            System.out.println("An Error has Occurred with the File Reader");
        }
        return substrings;
    }
}