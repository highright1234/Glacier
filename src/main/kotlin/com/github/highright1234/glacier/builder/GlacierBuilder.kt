package com.github.highright1234.glacier.builder

import com.github.highright1234.glacier.GlacierClient
import com.github.highright1234.glacier.GlacierServer
import com.github.highright1234.glacier.builder.node.ClientSettingNode
import com.github.highright1234.glacier.builder.node.ServerSettingNode
import com.github.highright1234.glacier.event.event.PacketReceivingEvent
import com.github.highright1234.glacier.protocol.packet.play.client.PlayerChatMessage
import net.kyori.adventure.text.Component.text
import java.net.InetSocketAddress

class GlacierBuilder

fun serverBuilder(
    host : String = "0.0.0.0",
    port : Int = 25565,
    configure : ServerConfiguration.() -> Unit = {},
    settingNode: ServerSettingNode.() -> Unit
) : GlacierServer {

    val server = GlacierServer();
    val configuration = ServerConfiguration().apply(configure);

    server
        .address(InetSocketAddress(host, port))
        .onlineMode(configuration.onlineMode)
        .connectionTimeout(configuration.connectionTimeout)

    ServerSettingNode().apply(settingNode).listeners.forEach {
        server.addListener(it)
    }

    return server
}

fun clientBuilder(
    version : Int,
    host : String = "0.0.0.0",
    port : Int,
    configure : ClientConfiguration.() -> Unit = {},
    settingNode: ClientSettingNode.() -> Unit
) : GlacierClient {

    val client = GlacierClient()
    val configuration = ClientConfiguration().apply(configure)

    client
        .version(version)
        .account(configuration.userName, configuration.password)
        .address(InetSocketAddress(host, port))
        .connectionTimeout(configuration.connectionTimeout)

    ClientSettingNode().apply(settingNode).listeners.forEach {
        client.addListener(it)
    }

    serverBuilder(configure = {
        onlineMode = true
    }) {
        listener(PacketReceivingEvent::class.java) {
            if (it.packet is PlayerChatMessage) {
                it.clientConnection.sendMessage(text("미안한데 그 기능은 지원 안해"))
            }
        }

        onPluginMessage("minecraft:brand") {
            it.clientConnection.sendPluginMessage("highright:idiot", ByteArray(0))
        }
    }.start()

    return client
}

