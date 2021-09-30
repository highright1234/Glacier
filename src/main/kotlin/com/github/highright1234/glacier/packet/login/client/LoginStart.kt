package com.github.highright1234.glacier.packet.login.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class LoginStart(var userName : String = "") : MinecraftPacket() {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {
        writeString(userName, buf, 16)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        userName = readString(buf, 16)
    }
}