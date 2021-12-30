package io.github.highright1234.glacier.event.event.server

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.ClientConnectionEvent
import io.github.highright1234.glacier.packet.handshake.client.HandshakePacket

data class ClientInitializingEvent(
    override val clientConnection: io.github.highright1234.glacier.ClientConnection,
    val handshakePacket: HandshakePacket
) : ClientConnectionEvent()