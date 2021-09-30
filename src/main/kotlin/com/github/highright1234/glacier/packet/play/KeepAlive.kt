package com.github.highright1234.glacier.packet.play

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

class KeepAlive : MinecraftPacket() {
    private var keepAliveID: Long = 0
    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeLong(keepAliveID)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        keepAliveID = buf.readLong()
    }
}