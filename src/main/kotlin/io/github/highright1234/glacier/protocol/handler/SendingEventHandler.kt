package io.github.highright1234.glacier.protocol.handler

import io.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelOutboundHandlerAdapter
import io.github.highright1234.glacier.event.EventManager
import io.netty.channel.ChannelPromise
import io.github.highright1234.glacier.event.event.PacketSendingEvent
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.protocol.PipelineUtil.clientConnection

data class SendingEventHandler(val eventManager : EventManager) : ChannelOutboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        if (msg is MinecraftPacket) {
            ctx.channel().clientConnection?.let { eventManager.callEvent(PacketSendingEvent(it, msg)) }
        }
    }
}