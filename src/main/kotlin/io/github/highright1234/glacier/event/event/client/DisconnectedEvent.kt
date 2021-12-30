package io.github.highright1234.glacier.event.event.client

import io.github.highright1234.glacier.event.Event

class DisconnectedEvent : Event() {
    enum class Reason {
        KICK,
        TIME_OUT,
        BANNED,
        CLOSED
    }
}