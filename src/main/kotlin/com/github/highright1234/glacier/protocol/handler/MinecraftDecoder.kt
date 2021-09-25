package com.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import com.github.highright1234.glacier.protocol.Protocol

data class MinecraftDecoder(var protocolType : Protocol.Type, val isServer : Boolean) : MessageToMessageDecoder<ByteBuf>() {

    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val packetID: Int = BufUtil.readVarInt(msg)
        val dir = if (isServer) protocolType.TO_SERVER else protocolType.TO_CLIENT
        val packet = dir.getPacket(packetID)
        packet!!.read(msg)
        if (msg.isReadable) {
            msg.release()
            return
        }
        out.add(packet)
    }
}