package com.github.highright1234.glacier.event.event

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent
import com.github.highright1234.glacier.event.Cancellable
import com.github.highright1234.glacier.protocol.MinecraftPacket

class PacketSendingEvent(
    override val clientConnection: ClientConnection,
    val packet: MinecraftPacket,
    override var isCancelled: Boolean = false
) : ClientConnectionEvent(), Cancellable