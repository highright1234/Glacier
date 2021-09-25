package com.github.highright1234.glacier.event.event

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent
import com.github.highright1234.glacier.event.Cancellable
import com.github.highright1234.glacier.protocol.MinecraftPacket

data class PacketReceivingEvent(
    override val clientConnection: ClientConnection,
    override var isCancelled: Boolean,
    var packet: MinecraftPacket
) : ClientConnectionEvent(), Cancellable