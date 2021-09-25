package com.github.highright1234.glacier.protocol.packet.status.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

class SLPRequest : MinecraftPacket() {
    override fun write(buf: ByteBuf) {}
    override fun read(buf: ByteBuf) {}
}