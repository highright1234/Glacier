package com.github.highright1234.glacier.protocol.handler

import com.github.highright1234.glacier.protocol.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.zip.Inflater

class CompressDecoder : ByteToMessageDecoder() {

    val decompressor : Inflater

    init {
        decompressor = Inflater()
    }

    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {
        val dataLength = `in`.readVarInt().value
        decompressor.setInput(`in`.array(), 0, dataLength)
        var outLength = decompressor.inflate(ByteArray(100))
        TODO()
    }
}