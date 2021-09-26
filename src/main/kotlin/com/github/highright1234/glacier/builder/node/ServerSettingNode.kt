package com.github.highright1234.glacier.builder.node

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.AbstractEventListener
import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.event.Listener
import com.github.highright1234.glacier.event.event.PluginMessageEvent
import com.github.highright1234.glacier.event.event.server.ClientConnectedEvent
import com.github.highright1234.glacier.event.event.server.LoginPluginResponseEvent
import com.github.highright1234.glacier.event.event.server.SLPRequestEvent

class ServerSettingNode : SettingNode() {

    val listeners = ArrayList<Listener>();

    fun <T : Event> listener(clazz: Class<T>, listener: AbstractEventListener<T>) {
        listeners.add(listener)
    }

    fun onPing(listener: AbstractEventListener<SLPRequestEvent>) {
        listener(SLPRequestEvent::class.java, listener)
    }

    fun onClientConnected(listener: AbstractEventListener<ClientConnectedEvent>) {
        listener(ClientConnectedEvent::class.java, listener)
    }

    fun onLoginPluginResponse(listener: (LoginPluginResponseEvent) -> Unit) {
        listener(LoginPluginResponseEvent::class.java, listener)
    }

    fun onPluginMessage(channel: String, task: (PluginMessageData) -> Unit) {
        listener(PluginMessageEvent::class.java) {
            if (channel == it.channel) {
                task.invoke(PluginMessageData(it.clientConnection!!, it.data))
            }
        }
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
