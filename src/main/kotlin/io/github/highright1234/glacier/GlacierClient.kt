package io.github.highright1234.glacier

import io.github.highright1234.glacier.event.EventManager
import java.net.InetSocketAddress
import io.netty.channel.ChannelInitializer
import kotlin.Throws
import java.lang.Exception
import io.github.highright1234.glacier.protocol.PipelineUtil
import io.github.highright1234.glacier.protocol.handler.MinecraftEncoder
import io.github.highright1234.glacier.protocol.handler.MinecraftDecoder
import io.github.highright1234.glacier.protocol.handler.ReceivingEventHandler
import io.github.highright1234.glacier.protocol.handler.SendingEventHandler
import io.netty.channel.ChannelFutureListener
import io.github.highright1234.glacier.packet.handshake.client.HandshakePacket
import io.github.highright1234.glacier.protocol.Protocol
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.socket.ServerSocketChannel

class GlacierClient {

    var userName = "HighRight"
    var password = ""

    fun account(userName : String, password : String = "") : io.github.highright1234.glacier.GlacierClient {
        this.userName = userName
        this.password = password
        return this
    }

    var isLogin = true

    fun login(value : Boolean) : io.github.highright1234.glacier.GlacierClient {
        isLogin = value
        return this
    }

    var address = InetSocketAddress("0.0.0.0", 25565)

    var connectionTimeout: Long = 5000

    fun connectionTimeout(value : Long) : io.github.highright1234.glacier.GlacierClient {
        connectionTimeout = value
        return this
    }

    var protocol: Protocol = Protocol()

    var version = 0

    var ping = 0

    val channel : Channel
        get() = clientChannel
    private lateinit var clientChannel : Channel

    private var channelInitializer: ChannelInitializer<ServerSocketChannel> =
        object : ChannelInitializer<ServerSocketChannel>() {
        @Throws(Exception::class)
        override fun initChannel(ch: ServerSocketChannel) {
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
            val serverConnection = io.github.highright1234.glacier.ServerConnection(ch)
            ch.attr(PipelineUtil.SERVER_CONNECTION).set(serverConnection)
            ch.attr(PipelineUtil.GLACIER_CLIENT).set(this@GlacierClient)

            ch.pipeline().addLast(PipelineUtil.RECEIVING_EVENT_CALLER, ReceivingEventHandler(eventManager))
            ch.pipeline().addFirst(PipelineUtil.SENDING_EVENT_CALLER, SendingEventHandler(eventManager))

            val handshakePacket = HandshakePacket()

            handshakePacket.protocolVersion = version
            handshakePacket.serverAddress = address.hostName
            handshakePacket.serverPort = address.port
            handshakePacket.nextState = if (isLogin) 2 else 1

            serverConnection.sendPacket(handshakePacket)
        }
    }

    var channelFutureListener = ChannelFutureListener {
        clientChannel = it.channel()
    }

    val eventManager = EventManager()

    fun version(version: Int): io.github.highright1234.glacier.GlacierClient {
        this.version = version
        protocol = Protocol.createProtocol(version)
        return this
    }

    fun address(socketAddress: InetSocketAddress): io.github.highright1234.glacier.GlacierClient {
        address = socketAddress
        return this
    }

    fun addListener(listener: Any): io.github.highright1234.glacier.GlacierClient {
        eventManager.registerListener(listener)
        return this
    }

    fun start() {
        check(version != 0) { "version value is not Declared" }
        Bootstrap()
            .channel(NioServerSocketChannel::class.java)
            .handler(channelInitializer)
            .group(io.github.highright1234.glacier.GlacierClient.Companion.group)
            .localAddress(address)
            .connect(address)
            .addListener(channelFutureListener)
    }

    fun close() {
        channel.close()
    }

    companion object {
        @JvmStatic
        private val group: EventLoopGroup = NioEventLoopGroup(
            0,
            ThreadFactoryBuilder().setNameFormat("Glacier Client Group IO Thread \$d").build()
        )
    }
}