package com.github.highright1234.glacier

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.Protocol
import com.github.highright1234.glacier.protocol.packet.DisconnectPacket
import com.github.highright1234.glacier.protocol.packet.play.PluginMessagePacket
import com.github.highright1234.glacier.protocol.packet.play.client.ClientSetting
import com.github.highright1234.glacier.protocol.packet.play.server.ChatMessage
import io.netty.channel.Channel
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

data class ClientConnection (
    val ch: Channel,
    val protocolType: Protocol.Type,
    val glacierServer: GlacierServer,
) : Audience {

    var clientSetting : ClientSetting = ClientSetting()

    var onGround : Boolean = false
    var x : Double = 0.0
    var y : Double = 0.0
    var z : Double = 0.0
    var yaw : Float = 0F
    var pitch : Float = 0F

    var protocolVersion = Protocol.Version.MINECRAFT_1_7_5
    var ping = 0

    fun sendPacket(packet: MinecraftPacket) {
        if (ch.isActive) {
            ch.writeAndFlush(packet)
        }
    }

    fun disconnect(message: Component) {
        if (protocolType === glacierServer.getProtocol(protocolVersion).LOGIN ||
            protocolType === glacierServer.getProtocol(protocolVersion).PLAY
        ) {
            sendPacket(DisconnectPacket(message))
        }
        ch.disconnect()
    }

    override fun sendMessage(message: Component) {
        sendPacket(ChatMessage(message))
    }

    fun sendPluginMessage(channel : String, data : ByteArray) {
        sendPacket(PluginMessagePacket(channel, data))
    }
}