package com.github.highright1234.glacier

import com.github.highright1234.glacier.event.EventManager
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

    var userName = "HighRight"
    var password = ""

    fun account(userName : String, password : String = "") : GlacierClient {
        this.userName = userName
        this.password = password
        return this
    }

    var address: SocketAddress = InetSocketAddress("0.0.0.0", 25565)

    var connectionTimeout: Long = 5000

    fun connectionTimeout(value : Long) : GlacierClient {
        connectionTimeout = value
        return this
    }

    var protocol: Protocol = Protocol()

    var version = 0

    var ping = 0

    private var channelInitializer: ChannelInitializer<SocketChannel> = object : ChannelInitializer<SocketChannel>() {
        @Throws(Exception::class)
        override fun initChannel(ch: SocketChannel) {
            val handshake = protocol.handshake
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_ENCODER,
                PipelineUtil.PACKET_ENCODER,
                MinecraftEncoder(handshake.toServer, true)
            )
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_DECODER,
                PipelineUtil.PACKET_DECODER,
                MinecraftDecoder(handshake.toServer, true)
            )
            ch.pipeline().addLast(PipelineUtil.RECEIVING_EVENT_CALLER, ReceivingEventHandler(eventManager))
            ch.pipeline().addFirst(PipelineUtil.SENDING_EVENT_CALLER, SendingEventHandler(eventManager))
        }
    }

    var channelFutureListener = ChannelFutureListener {
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