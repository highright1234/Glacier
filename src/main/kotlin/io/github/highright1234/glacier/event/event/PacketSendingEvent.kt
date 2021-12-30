package io.github.highright1234.glacier.event.event

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.ClientConnectionEvent
import io.github.highright1234.glacier.event.Cancellable
import io.github.highright1234.glacier.protocol.MinecraftPacket

class PacketSendingEvent(
    override val clientConnection: io.github.highright1234.glacier.ClientConnection,
    val packet: MinecraftPacket,
    override var isCancelled: Boolean = false
) : ClientConnectionEvent(), Cancellable