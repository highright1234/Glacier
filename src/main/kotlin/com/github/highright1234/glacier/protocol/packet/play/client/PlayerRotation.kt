package com.github.highright1234.glacier.protocol.packet.play.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class PlayerRotation(
    var yaw : Float,
    var pitch : Float,
    var onGround : Boolean
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeFloat(yaw)
        buf.writeFloat(pitch)
        buf.writeBoolean(onGround)
    }

    override fun read(buf: ByteBuf) {
        yaw = buf.readFloat()
        pitch = buf.readFloat()
        onGround = buf.readBoolean()
    }
}