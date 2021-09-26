package com.github.highright1234.glacier.builder.node

import com.github.highright1234.glacier.event.Event

abstract class SettingNode {
    fun <T : Event> listener(clazz : Class<T>, listener : (T) -> Unit) {

    }
}