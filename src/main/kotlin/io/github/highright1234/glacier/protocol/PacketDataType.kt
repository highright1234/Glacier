package io.github.highright1234.glacier.protocol

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

interface PacketDataType {
    @Throws(Exception::class)
    fun write(buf: ByteBuf)

    @Throws(Exception::class)
    fun read(buf: ByteBuf)
}