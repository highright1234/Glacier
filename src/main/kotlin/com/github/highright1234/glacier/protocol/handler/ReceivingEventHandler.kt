package com.github.highright1234.glacier.protocol.handler

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import com.github.highright1234.glacier.event.EventManager
import com.github.highright1234.glacier.protocol.PipelineUtil
import com.github.highright1234.glacier.event.event.PacketReceivingEvent

data class ReceivingEventHandler(val eventManager : EventManager) : ChannelInboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is MinecraftPacket) {
            if (PipelineUtil.getClientConnection(ctx.channel())?.let {
                    PacketReceivingEvent(it, msg)
                }?.let {
                    eventManager.callEvent( it ).isCancelled
                }!!
            ) {
                ctx.fireChannelRead(msg)
            }
        }
    }
}