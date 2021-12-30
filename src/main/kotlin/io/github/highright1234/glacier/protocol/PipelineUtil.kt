package io.github.highright1234.glacier.protocol

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.GlacierClient
import io.github.highright1234.glacier.ServerConnection
import io.netty.channel.*
import io.netty.util.AttributeKey

object PipelineUtil {

    val CLIENT_CONNECTION = AttributeKey.newInstance<io.github.highright1234.glacier.ClientConnection>("CLIENT_CONNECTION")!!

    val GLACIER_CLIENT = AttributeKey.newInstance<io.github.highright1234.glacier.GlacierClient>("GLACIER_CLIENT")!!
    val SERVER_CONNECTION = AttributeKey.newInstance<ServerConnection>("SERVER_CONNECTION")!!

    const val MINECRAFT_ENCODER = "minecraft-encoder"
    const val MINECRAFT_DECODER = "minecraft-decoder"
    const val PACKET_ENCODER = "packet-encoder"
    const val PACKET_DECODER = "packet-decoder"
    const val RECEIVING_EVENT_CALLER = "receiving-event-caller"
    const val SENDING_EVENT_CALLER = "sending-event-caller"

    val Channel.clientConnection: io.github.highright1234.glacier.ClientConnection? get() = attr(CLIENT_CONNECTION).get()

    val Channel.glacierClient : io.github.highright1234.glacier.GlacierClient? get() = attr(GLACIER_CLIENT).get()

    val Channel.serverConnection : ServerConnection? get() = attr(SERVER_CONNECTION).get()

}