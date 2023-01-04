package com.kenzie.appserver;

import com.kenzie.appserver.service.FishService;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.JsonFishConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.List;

@Component
public class FileEventListener {

    private long lastModified;
    private File file;
    private FishService fishService;

    public FileEventListener(FishService fishService) throws IOException {
        file = new File("C:/Users/zchal/kenzie/ata-capstone-project-tv/exe/TV_Data/data.txt");
        lastModified = file.lastModified();
        this.fishService = fishService;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkFileModified() {
//        System.out.println("FileModified");

        long modified = file.lastModified();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
            }
            if (modified > lastModified) {
                System.out.println("Modify detected");
                lastModified = modified;
                try (FileReader reader = new FileReader(file)) {
                    List<Fish> fishList = JsonFishConverter.convertToFishFromFile(file);
                    for (Fish fish : fishList) {
                        fishService.addNewFish(fish);
                        System.out.println(fish.toString());
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
