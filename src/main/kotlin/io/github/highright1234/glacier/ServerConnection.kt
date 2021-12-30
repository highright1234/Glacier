package io.github.highright1234.glacier

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.packet.play.PluginMessagePacket
import io.github.highright1234.glacier.packet.play.client.PlayerChatMessage
import io.netty.channel.ServerChannel

data class ServerConnection(
    val ch: ServerChannel,
) {

    fun disconnect() {
        ch.disconnect()
    }

    fun sendPacket(packet : MinecraftPacket) {
        ch.writeAndFlush(packet)
    }

    fun sendMessage(message : String) {
        sendPacket(PlayerChatMessage(message))
    }

    fun sendPluginMessage(channel : String, data : ByteArray) {
        sendPacket(PluginMessagePacket(channel, data))
    }
}