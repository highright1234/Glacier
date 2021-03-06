package io.github.highright1234.glacier.protocol.handler

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.GlacierServer
import io.github.highright1234.glacier.event.EventManager
import io.github.highright1234.glacier.event.event.PluginMessageEvent
import io.github.highright1234.glacier.event.event.server.ClientChatEvent
import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.packet.play.PluginMessagePacket
import io.github.highright1234.glacier.packet.play.client.*
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class ServerPlayHandler(
    glacierServer: GlacierServer,
    clientConnection : io.github.highright1234.glacier.ClientConnection
) : ChannelInboundHandlerAdapter() {

    private val eventManager : EventManager
    private val cli : io.github.highright1234.glacier.ClientConnection

    init {
        this.eventManager = glacierServer.eventManager
        this.cli = clientConnection
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is MinecraftPacket) {
            ctx.fireChannelRead(msg)
            return
        }
        when(msg) {
            is PlayerMovement -> cli.onGround = msg.onGround
            is PlayerPositionAndRotation -> {
                cli.x = msg.x
                cli.y = msg.y
                cli.z = msg.z
                cli.yaw = msg.yaw
                cli.pitch = msg.pitch
                cli.onGround = msg.onGround
            }
            is PlayerPosition -> {
                cli.x = msg.x
                cli.y = msg.y
                cli.z = msg.z
                cli.onGround = msg.onGround
            }
            is PlayerChatMessage -> eventManager.callEvent(ClientChatEvent(cli, msg.message))
            is PluginMessagePacket -> eventManager.callEvent(PluginMessageEvent(cli, msg.channel, msg.data))
            is ClientSetting -> cli.clientSetting = msg
        }
    }
}