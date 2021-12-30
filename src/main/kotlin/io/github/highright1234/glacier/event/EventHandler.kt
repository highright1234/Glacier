package io.github.highright1234.glacier.event

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class EventHandler(
    val ignoreCancelled : Boolean = false,
    val property : EventPriority = EventPriority.NORMAL
)