package com.github.highright1234.glacier.protocol.handler


import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import com.github.highright1234.glacier.protocol.Protocol

data class MinecraftEncoder(var protocolType : Protocol.Type, val isServer : Boolean) : MessageToByteEncoder<MinecraftPacket>() {

    @Throws(Exception::class)

    override fun encode(ctx: ChannelHandlerContext, msg: MinecraftPacket, out: ByteBuf) {
        val dir = if (isServer) protocolType.TO_CLIENT else protocolType.TO_SERVER
        BufUtil.Companion.writeVarInt(dir.getId(msg.javaClass)!!, out)
        msg.write(out)
    }
}