package com.github.highright1234.glacier.builder

import com.github.highright1234.glacier.builder.node.ClientSettingNode
import com.github.highright1234.glacier.GlacierClient
import com.github.highright1234.glacier.GlacierServer
import com.github.highright1234.glacier.builder.node.ServerSettingNode
import com.github.highright1234.glacier.packet.play.PluginMessagePacket
import java.net.InetSocketAddress

class GlacierBuilder : GlacierBuilderDsl

fun serverBuilder(
    host : String = "0.0.0.0",
    port : Int = 25565,
    config : ServerConfiguration.() -> Unit = {},
    settingNode: ServerSettingNode.() -> Unit
) : GlacierServer {

    val server = GlacierServer()
    val configuration = ServerConfiguration().apply(config)

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
    isLogin : Boolean,
    config : ClientConfiguration.() -> Unit = {},
    settingNode: ClientSettingNode.() -> Unit
) : GlacierClient {

    val client = GlacierClient()
    val configuration = ClientConfiguration().apply(config)

    client
        .version(version)
        .account(configuration.userName, configuration.password)
        .address(InetSocketAddress(host, port))
        .connectionTimeout(configuration.connectionTimeout)
        .login(isLogin)

    ClientSettingNode().apply(settingNode).listeners.forEach { client.addListener(it) }

    null?.let {
        serverBuilder(config = {
            onlineMode = true
        }) {
            receivingListener(PluginMessagePacket::class.java) {
                receivingListener(PluginMessagePacket::class.java) {
                    onLoginPluginResponse {

                    }
                }
            }
            onPluginMessage("minecraft:brand") {
                clientConnection.sendPluginMessage("highright:idiot", ByteArray(0))
            }
        }.start()
    }

    return client
}

