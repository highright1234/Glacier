package io.github.highright1234.glacier

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.protocol.Protocol
import io.github.highright1234.glacier.packet.DisconnectPacket
import io.github.highright1234.glacier.packet.play.PluginMessagePacket
import io.github.highright1234.glacier.packet.play.client.ClientSetting
import io.github.highright1234.glacier.packet.play.server.ActionBar
import io.github.highright1234.glacier.packet.play.server.ServerChatMessage
import io.github.highright1234.glacier.packet.play.server.PlayerListHeaderAndFooter
import io.netty.channel.Channel
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.text.Component

data class ClientConnection (
    val ch: Channel,
    var protocolType: Protocol.Type,
    val glacierServer: io.github.highright1234.glacier.GlacierServer,
    var protocolVersion: Int = Protocol.Version.MINECRAFT_1_7_5
) : Audience {

    var clientSetting : ClientSetting = ClientSetting()

    var onGround : Boolean = false
    var x : Double = 0.0
    var y : Double = 0.0
    var z : Double = 0.0
    var yaw : Float = 0F
    var pitch : Float = 0F

    var ping = 0

    fun sendPacket(packet: MinecraftPacket) {
        if (ch.isActive) {
            ch.writeAndFlush(packet)
        }
    }

    fun disconnect(message: Component) {
        if (protocolType is Protocol.Login || protocolType is Protocol.Play) {
            sendPacket(DisconnectPacket(message))
        }
        ch.disconnect()
    }

    fun sendPluginMessage(channel : String, data : ByteArray) {
        sendPacket(PluginMessagePacket(channel, data))
    }

    override fun sendMessage(message: Component) {
        sendPacket(ServerChatMessage(message))
    }

    override fun sendMessage(message: Component, type: MessageType) {
        sendPacket(ServerChatMessage(message, if (type == MessageType.CHAT) 0 else 1))
    }

    override fun sendPlayerListHeader(header: Component) {
        sendPacket(PlayerListHeaderAndFooter(header, Component.empty()))
    }

    override fun sendActionBar(message: Component) {
        sendPacket(ActionBar(message))
    }

    override fun sendPlayerListFooter(footer: Component) {
        sendPacket(PlayerListHeaderAndFooter(Component.empty(), footer))
    }

    override fun sendPlayerListHeaderAndFooter(header: Component, footer: Component) {
        sendPacket(PlayerListHeaderAndFooter(header, footer))
    }
}