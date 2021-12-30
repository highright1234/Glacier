package io.github.highright1234.glacier.event.event.server

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.ClientConnectionEvent

data class ClientConnectedEvent(
    override val clientConnection: io.github.highright1234.glacier.ClientConnection
) : ClientConnectionEvent()