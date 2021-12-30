package io.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.github.highright1234.glacier.protocol.BufUtil
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.protocol.PipelineUtil.clientConnection
import io.github.highright1234.glacier.protocol.PipelineUtil.glacierClient
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import io.github.highright1234.glacier.protocol.Protocol

data class MinecraftDecoder(var protocolType : Protocol.Type.DirectionData, val isServer : Boolean) : MessageToMessageDecoder<ByteBuf>() {
    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val packetID: Int = BufUtil.readVarInt(msg)
        val packet = protocolType.getPacket(packetID)!!
        packet.read(msg, if (isServer)
            ctx.channel().clientConnection!!.protocolVersion
            else ctx.channel().glacierClient!!.version
        )
        if (msg.isReadable) {
            msg.release()
            return
        }
        out.add(packet)
    }
}