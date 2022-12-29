package com.kenzie.appserver;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

public class ApplicationStartupGenerator {
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void generateStartupEvent() {
        EventObject event = new EventObject(this);
        for (EventListener listener : listeners) {
            listener.hashCode();
            // listener.hashCode(); ???
        }
    }
}