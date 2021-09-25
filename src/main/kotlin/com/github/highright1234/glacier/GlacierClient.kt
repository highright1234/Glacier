package com.github.highright1234.glacier

import java.net.SocketAddress
import java.net.InetSocketAddress
import io.netty.channel.ChannelInitializer
import kotlin.Throws
import java.lang.Exception
import com.github.highright1234.glacier.protocol.PipelineUtil
import com.github.highright1234.glacier.protocol.handler.MinecraftEncoder
import com.github.highright1234.glacier.protocol.handler.MinecraftDecoder
import com.github.highright1234.glacier.protocol.handler.ReceivingEventHandler
import com.github.highright1234.glacier.protocol.handler.SendingEventHandler
import io.netty.channel.ChannelFutureListener
import com.github.highright1234.glacier.event.Listener
import com.github.highright1234.glacier.protocol.Protocol
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.socket.SocketChannel

class GlacierClient {

    var address: SocketAddress = InetSocketAddress("0.0.0.0", 25565)

    val connectTimeout: Long = 5000

    var protocol: Protocol? = null

    var version = 0

    val ping = 0

    private val channelInitializer: ChannelInitializer<SocketChannel> = object : ChannelInitializer<SocketChannel>() {
        @Throws(Exception::class)
        override fun initChannel(ch: SocketChannel) {
            val handshake = protocol!!.HANDSHAKE
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_ENCODER,
                PipelineUtil.PACKET_ENCODER,
                MinecraftEncoder(handshake, true)
            )
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_DECODER,
                PipelineUtil.PACKET_DECODER,
                MinecraftDecoder(handshake, true)
            )
            ch.pipeline().addLast(PipelineUtil.RECEIVING_EVENT_CALLER, ReceivingEventHandler(eventManager))
            ch.pipeline().addFirst(PipelineUtil.SENDING_EVENT_CALLER, SendingEventHandler(eventManager))
        }
    }

    val channelFutureListener = ChannelFutureListener {
        // TODO
    }

    val eventManager = EventManager()

    fun version(version: Int): GlacierClient {
        this.version = version
        protocol = Protocol.createProtocol(version)
        return this
    }

    fun address(socketAddress: SocketAddress): GlacierClient {
        address = socketAddress
        return this
    }

    fun addListener(listener: Listener?): GlacierClient {
        eventManager.registerListener(listener!!)
        return this
    }

    fun start() {
        check(version != 0) { "version value is not Declared" }
        Bootstrap()
            .channel(NioServerSocketChannel::class.java)
            .handler(channelInitializer)
            .group(group)
            .localAddress(address)
            .connect(address)
            .addListener(channelFutureListener)
    }

    companion object {
        @JvmStatic
        private val group: EventLoopGroup = NioEventLoopGroup(
            0,
            ThreadFactoryBuilder().setNameFormat("Glacier Client Group IO Thread \$d").build()
        )
    }
}