package com.github.highright1234.glacier.protocol.handler

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelOutboundHandlerAdapter
import com.github.highright1234.glacier.event.EventManager
import io.netty.channel.ChannelPromise
import com.github.highright1234.glacier.event.event.PacketSendingEvent
import com.github.highright1234.glacier.protocol.PipelineUtil

data class SendingEventHandler(val eventManager : EventManager) : ChannelOutboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        if (msg is MinecraftPacket) {
            PipelineUtil.getClientConnection(ctx.channel())?.let {
                PacketSendingEvent(it, msg)
            }?.let {
                eventManager.callEvent(
                    it
                )
            }
        }
    }
}