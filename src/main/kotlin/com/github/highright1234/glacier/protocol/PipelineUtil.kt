package com.github.highright1234.glacier.protocol

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.GlacierClient
import io.netty.channel.*
import io.netty.util.AttributeKey

object PipelineUtil {
    val CONNECTION = AttributeKey.newInstance<ClientConnection>("CLIENT_CONNECTION")!!
    val GLACIER_CLIENT = AttributeKey.newInstance<GlacierClient>("GLACIER_CLIENT")!!

    const val MINECRAFT_ENCODER = "minecraft-encoder"
    const val MINECRAFT_DECODER = "minecraft-decoder"
    const val PACKET_ENCODER = "packet-encoder"
    const val PACKET_DECODER = "packet-decoder"
    const val RECEIVING_EVENT_CALLER = "receiving-event-caller"
    const val SENDING_EVENT_CALLER = "sending-event-caller"

    fun getClientConnection(ch: Channel): ClientConnection? {
        return ch.attr(CONNECTION).get()
    }

    fun getGlacierClient(ch: Channel) : GlacierClient? {
        return ch.attr(GLACIER_CLIENT).get()
    }
}