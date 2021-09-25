package com.github.highright1234.glacier.event

import com.github.highright1234.glacier.ClientConnection

abstract class ClientConnectionEvent : Event() {
    abstract val clientConnection: ClientConnection?
}