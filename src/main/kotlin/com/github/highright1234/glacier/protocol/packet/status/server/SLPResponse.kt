package com.github.highright1234.glacier.protocol.packet.status.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class SLPResponse(var jsonResponse : String) : MinecraftPacket() {


    override fun write(buf: ByteBuf) {
        writeString(jsonResponse, buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        jsonResponse = readString(buf)
    }
}