package io.github.highright1234.glacier.builder

import io.github.highright1234.glacier.builder.node.ClientSettingNode
import io.github.highright1234.glacier.GlacierClient
import io.github.highright1234.glacier.GlacierServer
import io.github.highright1234.glacier.builder.node.ServerSettingNode
import io.github.highright1234.glacier.packet.play.PluginMessagePacket
import java.net.InetSocketAddress

class GlacierBuilder : GlacierBuilderDsl

fun serverBuilder(
    host : String = "0.0.0.0",
    port : Int = 25565,
    config : ServerConfiguration.() -> Unit = {},
    settingInit: ServerSettingNode.() -> Unit
) : GlacierServer {

    val server = GlacierServer()
    val configuration = ServerConfiguration().apply(config)

    server
        .address(InetSocketAddress(host, port))
        .onlineMode(configuration.onlineMode)
        .connectionTimeout(configuration.connectionTimeout)

    ServerSettingNode().apply {
        settingInit()

        listeners.forEach {
            server.addListener(it)
        }
    }
    return server
}

fun clientBuilder(
    version : Int,
    host : String = "0.0.0.0",
    port : Int,
    isLogin : Boolean,
    config : ClientConfiguration.() -> Unit = {},
    settingInit: ClientSettingNode.() -> Unit
) : io.github.highright1234.glacier.GlacierClient {

    val client = io.github.highright1234.glacier.GlacierClient()
    val configuration = ClientConfiguration().apply(config)

    client
        .version(version)
        .account(configuration.userName, configuration.password)
        .address(InetSocketAddress(host, port))
        .connectionTimeout(configuration.connectionTimeout)
        .login(isLogin)

    ClientSettingNode().apply {
        settingInit()
        listeners.forEach {
            client.addListener(it)
        }
    }

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
                clientConnection!!.sendPluginMessage("highright:idiot", ByteArray(0))
            }
        }.start()
    }

    return client
}

