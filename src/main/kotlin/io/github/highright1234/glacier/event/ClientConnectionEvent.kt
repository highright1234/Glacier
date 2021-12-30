package io.github.highright1234.glacier.event

import io.github.highright1234.glacier.ClientConnection

abstract class ClientConnectionEvent : Event() {
    abstract val clientConnection: io.github.highright1234.glacier.ClientConnection?
}