package com.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class MinecraftCipherEncoder : MessageToByteEncoder<ByteBuf?>() {
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf?, out: ByteBuf) {
    }
}