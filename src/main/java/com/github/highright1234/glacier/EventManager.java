package com.github.highright1234.glacier;

import com.github.highright1234.glacier.event.Event;
import com.github.highright1234.glacier.event.EventHandler;
import com.github.highright1234.glacier.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventManager {

    private final HashMap<Listener, Set<Method>> listeners = new HashMap<>();

    public void registerListener(@NotNull Listener listener) {
        if (listeners.containsKey(listener)) return;
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.getAnnotation(EventHandler.class) == null) continue;
            if (method.getParameterCount() != 1) {
                throw new Error("parameter count is not 1");
            }
            if (!method.getParameterTypes()[0].isAssignableFrom(Event.class)) {
                throw new Error("parameter is not event");
            }
            listeners.computeIfAbsent(listener, k -> new HashSet<>());
            listeners.get(listener).add(method);
        }
    }

    public void unregisterListener(@NotNull Listener listener) {
        if (!listeners.containsKey(listener)) return;
        listeners.remove(listener);
    }

    public <T extends Event> T callEvent(Object event) {
        for (Listener listener : listeners.keySet()) {
            for (Method method : listeners.get(listener)) {
                try {
                    method.invoke(listener);
                } catch (Exception ignored) {}
            }
        }
        return event;
    }
}
