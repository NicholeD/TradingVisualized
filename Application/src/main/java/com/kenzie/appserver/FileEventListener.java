package com.kenzie.appserver;

import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class FileEventListener {

    private long lastModified;
    private File file;

    public FileEventListener() {
        file = new File("/path/to/data.txt");
        lastModified = file.lastModified();
    }

    @Scheduled(fixedDelay = 1)
    public void checkFileModified() {
        System.out.println("FileModified");

        long modified = file.lastModified();
        if (modified > lastModified) {
            System.out.println("Modify detected");
            lastModified = modified;
            try (FileReader reader = new FileReader(file)) {

            } catch (IOException e) {

            }
        }
    }
}
