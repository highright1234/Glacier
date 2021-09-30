package com.github.highright1234.glacier.builder.node

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.AbstractEventListener
import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.event.Listener
import com.github.highright1234.glacier.event.event.PacketReceivingEvent
import com.github.highright1234.glacier.event.event.PacketSendingEvent
import com.github.highright1234.glacier.event.event.PluginMessageEvent
import com.github.highright1234.glacier.event.event.server.ClientConnectedEvent
import com.github.highright1234.glacier.event.event.server.LoginPluginResponseEvent
import com.github.highright1234.glacier.event.event.server.SLPRequestEvent
import com.github.highright1234.glacier.protocol.MinecraftPacket

class ServerSettingNode : SettingNode() {

    val listeners = ArrayList<Listener>()

    override fun <T : Event> listener(clazz : Class<T>, listener: AbstractEventListener<T>) {
        listeners.add(listener)
    }

    override fun <T : Event> listener(clazz: Class<T>, listener: (T) -> Unit) {
        listener(clazz, object : AbstractEventListener<T>() {
            override fun listener(event: T) {
                listener(event)
            }
        })
    }

    override fun <T : MinecraftPacket> receivingListener(clazz: Class<T>, listener: PacketReceivingEvent.() -> Unit) {
        listener(PacketReceivingEvent::class.java) {
            if (it.packet.javaClass == clazz) {
                listener(it)
            }
        }
    }

    override fun <T : MinecraftPacket> sendingListener(clazz: Class<T>, listener: PacketSendingEvent.() -> Unit) {
        listener(PacketSendingEvent::class.java) {
            if (it.packet.javaClass == clazz) {
                listener(it)
            }
        }
    }

    fun onPing(listener: AbstractEventListener<SLPRequestEvent>) {
        listener(SLPRequestEvent::class.java, listener)
    }

    fun onClientConnected(listener: AbstractEventListener<ClientConnectedEvent>) {
        listener(ClientConnectedEvent::class.java, listener)
    }

    fun onLoginPluginResponse(listener: LoginPluginResponseEvent.() -> Unit) {
        listener(LoginPluginResponseEvent::class.java, listener)
    }

    fun onPluginMessage(channel: String, task: PluginMessageData.() -> Unit) {
        listener(PluginMessageEvent::class.java) {
            if (channel == it.channel) {
                task(PluginMessageData(it.clientConnection!!, it.data))
            }
        }
    }

    fun addCommand(commandName : String, vararg alias : String) {

    }

    data class PluginMessageData(val clientConnection : ClientConnection, val data : ByteArray) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PluginMessageData

            if (clientConnection != other.clientConnection) return false
            if (!data.contentEquals(other.data)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = clientConnection.hashCode()
            result = 31 * result + data.contentHashCode()
            return result
        }
    }
}
