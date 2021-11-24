package com.github.highright1234.glacier.protocol.handler

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import com.github.highright1234.glacier.packet.status.client.Ping
import com.github.highright1234.glacier.packet.handshake.client.HandshakePacket
import com.github.highright1234.glacier.packet.status.client.SLPRequest
import com.github.highright1234.glacier.SLPResponseData
import com.github.highright1234.glacier.event.event.client.SLPResponseEvent
import com.github.highright1234.glacier.event.event.server.SLPRequestEvent
import com.github.highright1234.glacier.packet.status.server.SLPResponse
import com.github.highright1234.glacier.packet.status.server.Pong
import com.github.highright1234.glacier.protocol.PipelineUtil
import java.lang.RuntimeException
import kotlin.random.Random

data class InitializeHandler(
    val isServer : Boolean,
    var ping : Ping = Ping(),
    var isLogin : Boolean = false,
) : ChannelInboundHandlerAdapter() {

    var pingTime : Long = 0
    var slpResponse: SLPResponse = SLPResponse()
    lateinit var state : State

    init {
        if (isServer) state = State.HANDSHAKE else if (isLogin) State.LOGIN else State.STATUS
    }

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is MinecraftPacket) {
            ctx.fireChannelRead(msg)
            return
        }

        if (isServer) {
            val clientConnection = PipelineUtil.getClientConnection(ctx.channel())!!
            when (state) {
                State.HANDSHAKE -> if (msg is HandshakePacket) {
                    clientConnection.protocolVersion = msg.protocolVersion
                    state = when (msg.nextState) {
                        1 -> State.STATUS
                        2 -> State.LOGIN
                        else -> throw WRONG_NEXT_STATE
                    }
                }
                State.STATUS -> if (msg is SLPRequest) {
                    val response: SLPResponseData = clientConnection.glacierServer.eventManager.callEvent(
                        SLPRequestEvent(
                            clientConnection,
                            clientConnection.glacierServer.slpResponseData
                        )
                    ).slpResponseData
                    clientConnection.sendPacket(SLPResponse(response))
                    state = State.PING
                } else {
                    throw WRONG_PACKET
                }

                State.PING -> {
                    if (msg !is Ping) throw WRONG_PACKET
                    clientConnection.sendPacket(Pong(ping.payLoad))
                }

                State.LOGIN -> {
                    TODO()
                }

                State.ENCRYPTION -> {
                    TODO()
                }
                else -> {
                    TODO()
                }
            }
        } else {
            val glacier = PipelineUtil.getGlacierClient(ctx.channel())!!
            val serverConnection = PipelineUtil.getServerConnection(ctx.channel())!!
            when (state) {
                State.HANDSHAKE -> throw WRONG_STATE_VALUE
                State.STATUS -> {

                    if (msg !is SLPResponse) throw WRONG_PACKET

                    state = State.PING
                    slpResponse = msg
                    ping = Ping(Random.nextLong())
                    serverConnection.sendPacket(ping)

                }
                State.PING -> {

                    if (msg !is Pong) throw WRONG_PACKET
                    if (ping.payLoad != msg.payLoad) throw WRONG_PACKET

                    val ping = pingTime - System.currentTimeMillis()
                    glacier.eventManager.callEvent(SLPResponseEvent(slpResponse, ping))

                }
                State.LOGIN -> {
                    TODO()
                }
                State.ENCRYPTION -> {
                    TODO()
                }
                else -> {
                    throw RuntimeException(" ran unreached code ")
                }
            }
        }
    }

    enum class State {
        HANDSHAKE, STATUS, PING, LOGIN, ENCRYPTION, LOGIN_SUCCESSFUL
    }

    companion object {
        private val WRONG_PACKET: Exception = RuntimeException("it's wrong packet")
        private val WRONG_NEXT_STATE: Exception = RuntimeException("it's wrong next state!")
        private val WRONG_STATE_VALUE: Exception = RuntimeException("wrong state value!")
    }
}