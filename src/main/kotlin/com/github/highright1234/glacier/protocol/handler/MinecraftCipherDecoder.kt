package com.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class MinecraftCipherDecoder : ByteToMessageDecoder() {
    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: List<Any>) {
    }
}