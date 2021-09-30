package com.github.highright1234.glacier.protocol.handler


import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import com.github.highright1234.glacier.protocol.PipelineUtil
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import com.github.highright1234.glacier.protocol.Protocol

data class MinecraftEncoder(var protocolType : Protocol.Type.DirectionData, val isServer : Boolean) : MessageToByteEncoder<MinecraftPacket>() {
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, msg: MinecraftPacket, out: ByteBuf) {

        BufUtil.writeVarInt(protocolType.getId(msg.javaClass)!!, out)

        msg.write(out, if (isServer)
            PipelineUtil.getClientConnection(ctx.channel())!!.protocolVersion
            else PipelineUtil.getGlacierClient(ctx.channel())!!.version
        )
    }
}