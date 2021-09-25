package com.github.highright1234.glacier

import java.net.SocketAddress
import java.net.InetSocketAddress
import io.netty.channel.EventLoopGroup
import java.util.TreeSet
import java.util.HashMap
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
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.channel.socket.SocketChannel
import java.util.ArrayList

class GlacierServer {

    val slpResponseData = SLPResponseData()

    private var address: SocketAddress = InetSocketAddress("0.0.0.0", 25565)

    var serverConnectTimeout: Long = 5000
    private val bossGroup: EventLoopGroup
    private val workerGroup: EventLoopGroup
    private val clientConnections: MutableList<ClientConnection> = ArrayList()
    fun getClientConnections(): Iterable<ClientConnection> {
        return clientConnections
    }

    val supportProtocols: Set<Int> = TreeSet()
    private val protocols: MutableMap<Int, Protocol?> = HashMap()
    fun getProtocol(protocolVersion: Int): Protocol {
        protocols.computeIfAbsent(protocolVersion) { k: Int? -> protocols.put(protocolVersion, Protocol()) }
        return protocols[protocolVersion]!!
    }

    var channelInitializer: ChannelInitializer<SocketChannel> = object : ChannelInitializer<SocketChannel>() {
        @Throws(Exception::class)
        override fun initChannel(ch: SocketChannel) {
            val handshake = getProtocol(Protocol.Version.MINECRAFT_1_7_5).HANDSHAKE
            val cliCon = ClientConnection(ch, handshake, this@GlacierServer)
            ch.attr(PipelineUtil.CONNECTION).set(cliCon)
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

    val channelFutureListener =
        ChannelFutureListener { future -> clientConnections.add(future.channel().attr(PipelineUtil.CONNECTION).get()) }

    val eventManager = EventManager()

    fun address(socketAddress: SocketAddress): GlacierServer {
        address = socketAddress
        return this
    }

    fun addListener(listener: Listener?): GlacierServer {
        eventManager.registerListener(listener!!)
        return this
    }

    fun start() {
        ServerBootstrap()
            .channel(NioServerSocketChannel::class.java)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_REUSEADDR, true)
            .childHandler(channelInitializer)
            .group(bossGroup, workerGroup)
            .localAddress(address)
            .bind().addListener(channelFutureListener)
    }

    init {
        bossGroup = NioEventLoopGroup(
            0,
            ThreadFactoryBuilder().setNameFormat("Glacier Server Boss Group IO Thread \$d").build()
        )
        workerGroup = NioEventLoopGroup(
            0,
            ThreadFactoryBuilder().setNameFormat("Glacier Server Worker Group IO Thread \$d").build()
        )
    }
}