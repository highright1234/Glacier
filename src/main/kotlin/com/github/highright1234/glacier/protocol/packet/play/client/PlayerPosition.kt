package com.github.highright1234.glacier.protocol.packet.play.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class PlayerPosition(
    var x : Double = 0.0,
    var y : Double = 0.0,
    var z : Double = 0.0,
    var onGround : Boolean = true
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
        buf.writeBoolean(onGround)
    }

    override fun read(buf: ByteBuf) {
        x = buf.readDouble()
        y = buf.readDouble()
        z = buf.readDouble()
        onGround = buf.readBoolean()
    }
}