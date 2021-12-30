package io.github.highright1234.glacier.packet.play.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class PlayerMovement(
    var onGround : Boolean
) : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeBoolean(onGround)
    }

    override fun read(buf: ByteBuf, version: Int) {
        onGround = buf.readBoolean()
    }
}