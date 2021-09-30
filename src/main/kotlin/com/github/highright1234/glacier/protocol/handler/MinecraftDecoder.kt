package com.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import com.github.highright1234.glacier.protocol.PipelineUtil
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import com.github.highright1234.glacier.protocol.Protocol

data class MinecraftDecoder(var protocolType : Protocol.Type.DirectionData, val isServer : Boolean) : MessageToMessageDecoder<ByteBuf>() {
    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val packetID: Int = BufUtil.readVarInt(msg).value
        val packet = protocolType.getPacket(packetID)
        packet!!.read(msg, if (isServer)
            PipelineUtil.getClientConnection(ctx.channel())!!.protocolVersion
            else PipelineUtil.getGlacierClient(ctx.channel()!!)!!.version
        )
        if (msg.isReadable) {
            msg.release()
            return
        }
        out.add(packet)
    }
}