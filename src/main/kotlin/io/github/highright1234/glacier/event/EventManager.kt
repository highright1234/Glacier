package io.github.highright1234.glacier.event

import io.github.highright1234.glacier.event.event.ThrownThrowableEvent
import java.util.HashMap
import java.util.HashSet
import java.lang.reflect.Method

class EventManager {

    private val listeners = HashMap<Any, MutableSet<Method>>()
    private val methodMap = HashMap<Method, Any>()
    private val eventMap = HashMap<Class<out Event>, HashSet<Method>>()

    fun registerListener(listener: Any) {
        if (listeners.containsKey(listener)) return
        listeners[listener] = HashSet<Method>()
        for (method in listener.javaClass.declaredMethods) {
            method.getAnnotation(EventHandler::class.java) ?: continue
            if (method.parameterCount != 1) {
                throw IllegalArgumentException("parameter count is not 1")
            }
            if (!method.parameterTypes[0].isAssignableFrom(Event::class.java)) {
                throw IllegalArgumentException("parameter is not event")
            }
            listeners[listener]!!.add(method)
        }
    }

    fun unregisterListener(listener: Any) {
        if (!listeners.containsKey(listener)) return
        listeners[listener]?.forEach {
            methodMap -= it
            eventMap[it.parameterTypes[0].asSubclass(Event::class.java)]?.let { handler ->
                handler -= it
            }
        }
        listeners -= listener
    }

    fun <T : Event> callEvent(event: T): T {
        var nowEvent = event.javaClass.asSubclass(Event::class.java)
        val classes = ArrayList<Class<out Event>>()
        while (nowEvent != Event::class.java) {
            nowEvent.superclass?.let {
                classes += nowEvent
                nowEvent = nowEvent.superclass.asSubclass(Event::class.java)
            } ?: break
        }
        val basicListeners = listeners.keys.filterIsInstance<BasicListener>()
        EventPriority.values().forEach { priority ->
            basicListeners.forEach { it.run(event, priority) }
            classes.forEach { clazz ->
                eventMap[clazz]?.let { collection ->

                    collection.forEach { method ->
                        methodMap[method]?.let { listener ->

                            runCatching { method(listener) }.exceptionOrNull()?.let { throwable ->
                                callEvent(ThrownThrowableEvent(throwable))
                                return event
                            }

                        }
                    }

                }
            }
        }
        return event
    }
}