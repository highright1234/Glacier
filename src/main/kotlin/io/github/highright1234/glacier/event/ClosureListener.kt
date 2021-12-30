package io.github.highright1234.glacier.event

open class ClosureListener<T : Event>(
    private val clazz : Class<T>, private val listener : (T) -> Unit
) : BasicListener() {
    override fun run(event: Event, priority: EventPriority) {
        if (clazz.isAssignableFrom(event::class.java)) {
            @Suppress("UNCHECKED_CAST")
            listener(event as T)
        }
    }
}