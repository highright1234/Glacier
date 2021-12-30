package io.github.highright1234.glacier.packet.play.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.protocol.readString
import io.github.highright1234.glacier.protocol.writeString
import io.netty.buffer.ByteBuf

data class PlayerChatMessage(
    var message : String = ""
) : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeString(message, 256)
    }

    override fun read(buf: ByteBuf, version: Int) {
        message = buf.readString(256)
    }
}