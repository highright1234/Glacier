package com.github.highright1234.glacier.protocol.handler

import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelInboundHandlerAdapter
import java.util.HashMap
import io.netty.channel.ChannelHandlerContext
import com.github.highright1234.glacier.protocol.packet.play.KeepAlive

data class KeepAliveHandler(

    private val isServer: Boolean = false,
    // ms
    val sendDelay: Int = 5000,
    val kickTime: Int = 30000,
    val schedulerData: HashMap<Long, Int> = HashMap<Long, Int>(),
    val timeData: HashMap<Long, Long> = HashMap<Long, Long>()

) : ChannelInboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is KeepAlive) {
            ctx.fireChannelRead(msg)
            return
        }
        if (isServer) {
        } else {
        }
    }

    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        if (isServer) {
            //
        }
    }

    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext) {
        if (isServer) {
        }
    }
}