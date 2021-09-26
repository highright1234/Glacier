package com.github.highright1234.glacier.protocol.handler

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import com.github.highright1234.glacier.protocol.packet.status.client.Ping
import com.github.highright1234.glacier.protocol.packet.handshake.client.HandshakePacket
import com.github.highright1234.glacier.protocol.packet.status.client.SLPRequest
import com.github.highright1234.glacier.SLPResponseData
import com.github.highright1234.glacier.event.event.server.SLPRequestEvent
import com.github.highright1234.glacier.protocol.packet.status.server.SLPResponse
import com.google.gson.Gson
import com.github.highright1234.glacier.protocol.packet.status.server.Pong
import java.lang.RuntimeException
import java.lang.IllegalStateException

data class InitializeHandler(
    val clientConnection: ClientConnection?,
    var state : State = State.HANDSHAKE,
    var isLogin : Boolean = false,
    var ping : Ping,
    var isServer : Boolean = clientConnection != null
) : ChannelInboundHandlerAdapter() {

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is MinecraftPacket) {
            ctx.fireChannelRead(msg)
            return
        }
        if (clientConnection != null) {
            when (state) {
                State.HANDSHAKE -> if (msg is HandshakePacket) {
                    clientConnection.protocolVersion = msg.protocolVersion
                    state = when (msg.nextState) {
                        1 -> {
                            State.STATUS
                        }
                        2 -> {
                            State.LOGIN
                        }
                        else -> {
                            throw WRONG_NEXT_STATE
                        }
                    }
                }
                State.STATUS -> if (msg is SLPRequest) {
                    val response: SLPResponseData = clientConnection.glacierServer.eventManager.callEvent(
                        SLPRequestEvent(
                            clientConnection,
                            clientConnection.glacierServer.slpResponseData
                        )
                    ).slpResponseData
                    clientConnection.sendPacket(SLPResponse(Gson().toJson(response)))
                } else {
                    throw WRONG_PACKET
                }
                State.PING -> if (msg is Ping) {
                    clientConnection.sendPacket(Pong(ping.payLoad))
                } else {
                    throw WRONG_PACKET
                }
            }
        } else {
            when (state) {
                State.HANDSHAKE -> throw WRONG_STATE_VALUE
                State.STATUS -> {
                }
                State.PING -> {
                    val pong = msg as Pong
                    if (ping.payLoad == pong.payLoad) {
                        TODO()
                    }
                }
                State.LOGIN -> {

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
        private val NULL_PING: Exception = IllegalStateException("you did not send the ping")
    }
}