package com.github.highright1234.glacier.event.event.server

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent

data class ClientChatEvent(
    override val clientConnection: ClientConnection,
    val message : String
) : ClientConnectionEvent()