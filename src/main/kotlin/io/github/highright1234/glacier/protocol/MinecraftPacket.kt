package io.github.highright1234.glacier.protocol

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

abstract class MinecraftPacket : BufUtil() {
    @Throws(Exception::class)
    abstract fun write(buf: ByteBuf, version : Int)
    @Throws(Exception::class)
    abstract fun read(buf: ByteBuf, version : Int)
}