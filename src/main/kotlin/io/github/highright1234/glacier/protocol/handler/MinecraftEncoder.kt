package io.github.highright1234.glacier.protocol.handler


import io.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.github.highright1234.glacier.protocol.BufUtil
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.protocol.PipelineUtil.clientConnection
import io.github.highright1234.glacier.protocol.PipelineUtil.glacierClient
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.github.highright1234.glacier.protocol.Protocol

data class MinecraftEncoder(var protocolType : Protocol.Type.DirectionData, val isServer : Boolean) : MessageToByteEncoder<MinecraftPacket>() {
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, msg: MinecraftPacket, out: ByteBuf) {

        BufUtil.writeVarInt(protocolType.getId(msg.javaClass)!!, out)

        msg.write(out, if (isServer)
            ctx.channel().clientConnection!!.protocolVersion
            else ctx.channel().glacierClient!!.version
        )
    }
}