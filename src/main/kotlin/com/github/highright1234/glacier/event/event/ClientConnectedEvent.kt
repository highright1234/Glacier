package com.github.highright1234.glacier.event.event

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent

data class ClientConnectedEvent(
    override val clientConnection: ClientConnection
) : ClientConnectionEvent()