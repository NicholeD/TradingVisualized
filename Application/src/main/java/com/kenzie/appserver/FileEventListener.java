package com.kenzie.appserver;

import com.kenzie.appserver.service.FishService;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.JsonFishConverter;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class FileEventListener {

    private long lastModified;
    private File file;
    private FishService fishService;

    public FileEventListener(FishService fishService) {
        file = new File("\\exe\\TV_Data\\data.txt");
        lastModified = file.lastModified();
        this.fishService = fishService;
    }

    @Scheduled(fixedDelay = 1)
    public void checkFileModified() {
        System.out.println("FileModified");

        long modified = file.lastModified();
        if (modified > lastModified) {
            System.out.println("Modify detected");
            lastModified = modified;
            try (FileReader reader = new FileReader(file)) {
                List<Fish> fishList = JsonFishConverter.convertToFishFromFile(file);
                for(Fish fish : fishList) {
                    fishService.addNewFish(fish);
                    System.out.println(fish.toString());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
