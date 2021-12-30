package io.github.highright1234.glacier.event

abstract class BasicListener {
    abstract fun run(event: Event, priority : EventPriority)
}