package com.github.highright1234.glacier.protocol.packet.status.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class Ping(var payLoad: Long = 0) : MinecraftPacket() {

    override fun write(buf: ByteBuf) {
        buf.writeLong(payLoad)
    }

    override fun read(buf: ByteBuf) {
        payLoad = buf.readLong()
    }
}