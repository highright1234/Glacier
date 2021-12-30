package io.github.highright1234.glacier.packet.handshake.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class LegacyServerListPing(var payload : Short) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        buf.writeByte(payload.toInt())
    }

    override fun read(buf: ByteBuf, version: Int) {
        payload = buf.readUnsignedByte()
    }
}