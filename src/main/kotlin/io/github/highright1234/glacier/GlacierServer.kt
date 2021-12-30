package io.github.highright1234.glacier

import io.github.highright1234.glacier.event.EventManager
import java.net.SocketAddress
import java.net.InetSocketAddress
import io.netty.channel.EventLoopGroup
import java.util.TreeSet
import java.util.HashMap
import io.netty.channel.ChannelInitializer
import kotlin.Throws
import java.lang.Exception
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.protocol.handler.MinecraftEncoder
import io.github.highright1234.glacier.protocol.handler.MinecraftDecoder
import io.github.highright1234.glacier.protocol.handler.ReceivingEventHandler
import io.github.highright1234.glacier.protocol.handler.SendingEventHandler
import io.netty.channel.ChannelFutureListener
import io.github.highright1234.glacier.protocol.Protocol
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.channel.socket.SocketChannel
import java.util.ArrayList

class GlacierServer {

    val slpResponseData = SLPResponseData()
    var onlineMode = false

    fun onlineMode(value : Boolean) : GlacierServer {
        onlineMode = value
        return this
    }

    private var address: SocketAddress = InetSocketAddress("0.0.0.0", 25565)

    var connectionTimeout: Long = 5000

    fun connectionTimeout(value : Long) : GlacierServer {
        connectionTimeout = value
        return this
    }

    var bossGroup: EventLoopGroup = NioEventLoopGroup(
        0,
        ThreadFactoryBuilder().setNameFormat("Glacier Server Boss Group IO Thread \$d").build()
    )
    var workerGroup: EventLoopGroup = NioEventLoopGroup(
        0,
        ThreadFactoryBuilder().setNameFormat("Glacier Server Worker Group IO Thread \$d").build()
    )
    val clientConnections: MutableList<io.github.highright1234.glacier.ClientConnection> = ArrayList()

    val supportProtocols: Set<Int> = TreeSet()
    private val protocols: MutableMap<Int, Protocol> = HashMap()

    fun getProtocol(protocolVersion: Int): Protocol {
        protocols[protocolVersion] ?: run { protocols[protocolVersion] = Protocol() }
        return protocols[protocolVersion]!!
    }

    var channelInitializer: ChannelInitializer<SocketChannel> = object : ChannelInitializer<SocketChannel>() {
        @Throws(Exception::class)
        override fun initChannel(ch: SocketChannel) {
            val handshake = getProtocol(Protocol.Version.MINECRAFT_1_7_5).handshake
            val cliCon = io.github.highright1234.glacier.ClientConnection(ch, handshake, this@GlacierServer)
            ch.attr(PipelineUtil.CLIENT_CONNECTION).set(cliCon)
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_ENCODER,
                PipelineUtil.PACKET_ENCODER,
                MinecraftEncoder(handshake.toClient, true)
            )
            ch.pipeline().addAfter(
                PipelineUtil.MINECRAFT_DECODER,
                PipelineUtil.PACKET_DECODER,
                MinecraftDecoder(handshake.toClient, true)
            )
            ch.pipeline().addLast(PipelineUtil.RECEIVING_EVENT_CALLER, ReceivingEventHandler(eventManager))
            ch.pipeline().addFirst(PipelineUtil.SENDING_EVENT_CALLER, SendingEventHandler(eventManager))
        }
    }

    val channelFutureListener =
        ChannelFutureListener {
                future -> clientConnections.add(future.channel().attr(PipelineUtil.CLIENT_CONNECTION).get())
        }

    val eventManager = EventManager()

    fun address(socketAddress: SocketAddress): GlacierServer {
        address = socketAddress
        return this
    }

    fun addListener(listener: Any): GlacierServer {
        eventManager.registerListener(listener)
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
}