package com.github.highright1234.glacier

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.Protocol
import com.github.highright1234.glacier.protocol.packet.DisconnectPacket
import io.netty.channel.Channel
import net.kyori.adventure.text.Component

data class ClientConnection (
    val ch: Channel,
    val protocolType: Protocol.Type,
    val glacierServer: GlacierServer
) {

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
}