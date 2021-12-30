package io.github.highright1234.glacier.packet.play.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class PlayerPositionAndRotation(
    var x : Double,
    var y : Double,
    var z : Double,
    var yaw : Float,
    var pitch : Float,
    var onGround : Boolean
) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
        buf.writeFloat(yaw)
        buf.writeFloat(pitch)
        buf.writeBoolean(onGround)
    }

    override fun read(buf: ByteBuf, version: Int) {
        x = buf.readDouble()
        y = buf.readDouble()
        z = buf.readDouble()
        yaw = buf.readFloat()
        pitch = buf.readFloat()
        onGround = buf.readBoolean()
    }
}