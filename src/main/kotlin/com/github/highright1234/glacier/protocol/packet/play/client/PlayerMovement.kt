package com.github.highright1234.glacier.protocol.packet.play.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class PlayerMovement(
    var onGround : Boolean
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeBoolean(onGround)
    }

    override fun read(buf: ByteBuf) {
        onGround = buf.readBoolean()
    }
}