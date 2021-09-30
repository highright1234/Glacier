package com.github.highright1234.glacier.packet.play.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

class DeclareCommands : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {

    }

    override fun read(buf: ByteBuf, version: Int) {

    }
}