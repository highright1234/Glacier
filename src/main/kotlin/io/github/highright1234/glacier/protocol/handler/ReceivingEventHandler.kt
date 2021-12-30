package io.github.highright1234.glacier.protocol.handler

import io.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import io.github.highright1234.glacier.event.EventManager
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.event.event.PacketReceivingEvent
import io.github.highright1234.glacier.protocol.PipelineUtil.clientConnection

data class ReceivingEventHandler(val eventManager : EventManager) : ChannelInboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is MinecraftPacket) {
            if (ctx.channel().clientConnection?.let {
                    eventManager.callEvent( PacketReceivingEvent(it, msg) ).isCancelled
                } == true
            ) {
                ctx.fireChannelRead(msg)
            }
        }
    }
}