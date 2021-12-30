package io.github.highright1234.glacier.builder.node

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.*
import io.github.highright1234.glacier.event.event.PacketReceivingEvent
import io.github.highright1234.glacier.event.event.PacketSendingEvent
import io.github.highright1234.glacier.event.event.PluginMessageEvent
import io.github.highright1234.glacier.event.event.server.ClientConnectedEvent
import io.github.highright1234.glacier.event.event.server.LoginPluginResponseEvent
import io.github.highright1234.glacier.event.event.server.SLPRequestEvent
import io.github.highright1234.glacier.protocol.MinecraftPacket

class ServerSettingNode : SettingNode() {

    val listeners = ArrayList<Any>()

    override fun <T : Event> listener(clazz : Class<T>, listener: Any) {
        listeners.add(listener)
    }

    override fun <T : Event> listener(clazz: Class<T>, listener: T.() -> Unit) {
        listener(clazz, object : BasicListener() {
            override fun run(event: Event, priority: EventPriority) {
                if (event.javaClass == clazz) {
                    @Suppress("UNCHECKED_CAST")
                    listener(event as T)
                }
            }
        })
    }

    override fun <T : MinecraftPacket> receivingListener(clazz: Class<T>, listener: PacketReceivingEvent.() -> Unit) {
        listener(PacketReceivingEvent::class.java) {
            listener(this)
        }
    }

    override fun <T : MinecraftPacket> sendingListener(clazz: Class<T>, listener: PacketSendingEvent.() -> Unit) {
        listener(PacketSendingEvent::class.java) {
            listener(this)
        }
    }

    fun onLoginPluginResponse(listener: LoginPluginResponseEvent.() -> Unit) {
        listener(LoginPluginResponseEvent::class.java, listener)
    }

    fun onPluginMessage(ch: String, task: PluginMessageEvent.() -> Unit) {
        listener(PluginMessageEvent::class.java) {
            if (ch == channel) {
                task(this)
            }
        }
    }

    fun addCommand(commandName : String, vararg alias : String) {
        TODO()
    }

    data class PluginMessageData(val clientConnection : io.github.highright1234.glacier.ClientConnection, val data : ByteArray) {
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
