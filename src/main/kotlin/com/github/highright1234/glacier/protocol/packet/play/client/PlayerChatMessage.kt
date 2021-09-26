package com.github.highright1234.glacier.protocol.packet.play.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.readString
import com.github.highright1234.glacier.protocol.writeString
import io.netty.buffer.ByteBuf

data class PlayerChatMessage(
    var message : String
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeString(message, 256)
    }

    override fun read(buf: ByteBuf) {
        message = buf.readString(256)
    }
}