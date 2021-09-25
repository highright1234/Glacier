package com.github.highright1234.glacier.protocol

import com.github.highright1234.glacier.ClientConnection
import io.netty.channel.*
import io.netty.util.AttributeKey

object PipelineUtil {
    val CONNECTION = AttributeKey.newInstance<ClientConnection>("CLIENT_CONNECTION")!!
    const val MINECRAFT_ENCODER = "minecraft-encoder"
    const val MINECRAFT_DECODER = "minecraft-decoder"
    const val PACKET_ENCODER = "packet-encoder"
    const val PACKET_DECODER = "packet-decoder"
    const val RECEIVING_EVENT_CALLER = "receiving-event-caller"
    const val SENDING_EVENT_CALLER = "sending-event-caller"
    fun getClientConnection(ch: Channel): ClientConnection? {
        return ch.attr(CONNECTION).get()
    }
}