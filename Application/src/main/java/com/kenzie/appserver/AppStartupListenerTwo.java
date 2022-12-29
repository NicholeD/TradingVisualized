package com.kenzie.appserver;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;


/* NICHOLE - DISREGARD THESE 2 CLASSES, I WAS COMPARING THEM ALONGSIDE SOME OTHER IDEAS I HAD
THESE ARE ESSENTIALLY DUMMY CLASSES WHILE I PLAY WITH THE POSSIBILITIES FOR HOW I WANT TO TRY AND INTEGRATE ALL OF THIS.
PLEASE LET ME KNOW WHAT DIRECTIONS YOU ADVISE OR WHERE YOU THINK I SHOULD BE LOOKING TO PROPERLY BUILD THIS!
 */

class StartupListener implements EventListener {
    public void handleStartupEvent(EventObject event) {
        // Read the contents of the text file
        String text = readTextFile("text.txt");

        // Print the contents of the text file to the console
        System.out.println(text);
    }

    private String readTextFile(String s) {
    }
}

// Event generator class
class StartupEventGenerator {
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void generateStartupEvent() {
        EventObject event = new EventObject(this);
        for (EventListener listener : listeners) {
            listener.handleStartupEvent(event);
        }
    }
}