package com.github.highright1234.glacier

import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.event.EventHandler
import com.github.highright1234.glacier.event.Listener
import java.util.HashMap
import java.lang.Error
import java.util.HashSet
import java.lang.Exception
import java.lang.reflect.Method

class EventManager {
    private val listeners = HashMap<Listener, MutableSet<Method>>()
    fun registerListener(listener: Listener) {
        if (listeners.containsKey(listener)) return
        for (method in listener.javaClass.declaredMethods) {
            if (method.getAnnotation(EventHandler::class.java) == null) continue
            if (method.parameterCount != 1) {
                throw Error("parameter count is not 1")
            }
            if (!method.parameterTypes[0].isAssignableFrom(Event::class.java)) {
                throw Error("parameter is not event")
            }
            listeners.computeIfAbsent(listener) { k: Listener? -> HashSet() }
            listeners[listener]!!.add(method)
        }
    }

    fun unregisterListener(listener: Listener) {
        if (!listeners.containsKey(listener)) return
        listeners.remove(listener)
    }

    fun <T : Event> callEvent(event: T): T {
        for (listener in listeners.keys) {
            for (method in listeners[listener]!!) {
                if (method.parameters[0].type.isAssignableFrom(event::class.java)) {
                    try {
                        method.invoke(listener, event)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return event
    }
}