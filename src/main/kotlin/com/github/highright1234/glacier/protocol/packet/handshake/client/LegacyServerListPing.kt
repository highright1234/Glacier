package com.github.highright1234.glacier.protocol.packet.handshake.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class LegacyServerListPing(var payload : Short) : MinecraftPacket() {

    override fun write(buf: ByteBuf) {
        buf.writeByte(payload.toInt())
    }

    override fun read(buf: ByteBuf) {
        payload = buf.readUnsignedByte()
    }
}