package com.github.highright1234.glacier;

import com.github.highright1234.glacier.event.Event;
import com.github.highright1234.glacier.event.EventHandler;
import com.github.highright1234.glacier.event.Listener;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EventManager {

    private static EventManager eventManager;

    private final HashMap<Listener, HashMap<Class<? extends Event>, Method>> listeners = new HashMap<>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public EventManager() {
        if (eventManager == null) {
            eventManager = this;
        }
    }

    public void registerListener(Listener listener) {
        if (listeners.containsKey(listener)) return;
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.getAnnotation(EventHandler.class) == null) continue;
            if (method.getParameterCount() != 1) {
                throw new Error("parameter count is not 1");
            }
            if (!method.getParameterTypes()[0].isAssignableFrom(Event.class)) {

            }

        }
    }

    public void unregisterListener(Listener listener) {
        if (!listeners.containsKey(listener)) return;
        listeners.remove(listener);
    }
}
