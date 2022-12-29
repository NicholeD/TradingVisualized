package com.kenzie.appserver;


import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;


@Component
public abstract class ApplicationStartUpListener implements EventListener {
    public void handleStartupEvent(EventObject event) {
        // Open the text file in a text editor
        openTextFile("text.txt");
    }

            public void openTextFile(String fileName) {
                try {
                    File file = new File(fileName);
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//    @EventListener
//    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Perform any application start-up tasks
        // Internally listen for text file as opposed to making an external url call?



