package com.github.highright1234.glacier.event

abstract class AbstractEventListener<T : Event?> : Listener {
    @EventHandler
    abstract fun listener(event: T)
}