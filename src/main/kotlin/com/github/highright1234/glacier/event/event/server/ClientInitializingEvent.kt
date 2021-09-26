package com.github.highright1234.glacier.event.event.server

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent
import com.github.highright1234.glacier.protocol.packet.handshake.client.HandshakePacket

data class ClientInitializingEvent(
    override val clientConnection: ClientConnection,
    val handshakePacket: HandshakePacket
) : ClientConnectionEvent()