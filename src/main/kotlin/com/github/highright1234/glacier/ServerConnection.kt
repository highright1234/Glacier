package com.github.highright1234.glacier

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.packet.play.PluginMessagePacket
import com.github.highright1234.glacier.packet.play.client.PlayerChatMessage
import io.netty.channel.ServerChannel

data class ServerConnection(
    val ch: ServerChannel
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