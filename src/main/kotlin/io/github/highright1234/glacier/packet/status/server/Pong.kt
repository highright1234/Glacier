package io.github.highright1234.glacier.packet.status.server

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class Pong(var payLoad : Long = 0) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        buf.writeLong(payLoad)
    }

    override fun read(buf: ByteBuf, version: Int) {
        payLoad = buf.readLong()
    }
}