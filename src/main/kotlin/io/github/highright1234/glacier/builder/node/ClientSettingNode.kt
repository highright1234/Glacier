package io.github.highright1234.glacier.builder.node

import io.github.highright1234.glacier.event.BasicListener
import io.github.highright1234.glacier.event.Event
import io.github.highright1234.glacier.event.EventPriority
import io.github.highright1234.glacier.event.event.PacketReceivingEvent
import io.github.highright1234.glacier.event.event.PacketSendingEvent
import io.github.highright1234.glacier.event.event.PluginMessageEvent
import io.github.highright1234.glacier.event.event.JoiningCompleteEvent
import io.github.highright1234.glacier.event.event.client.LoginPluginRequestEvent
import io.github.highright1234.glacier.event.event.client.SLPResponseEvent
import io.github.highright1234.glacier.packet.datatype.Identifier
import io.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.collections.ArrayList

class ClientSettingNode : SettingNode() {

    val listeners = ArrayList<Any>()

    override fun <T : Event> listener(clazz: Class<T>, listener: Any) {
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
            if (packet.javaClass == clazz) {
                listener(this)
            }
        }
    }

    override fun <T : MinecraftPacket> sendingListener(clazz: Class<T>, listener: PacketSendingEvent.() -> Unit) {
        listener(PacketSendingEvent::class.java) {
            if (packet.javaClass == clazz) {
                listener(this)
            }
        }
    }

    fun onJoiningComplete(task : JoiningCompleteEvent.() -> Unit) {
        listener(JoiningCompleteEvent::class.java) {
            task(this)
        }
    }

    fun onSLPResponseReceiving(task: SLPResponseEvent.() -> Unit) {
        listener(SLPResponseEvent::class.java) {
            task(this)
        }
    }

    fun onLoginPluginRequest(channel : Identifier, task: LoginPluginRequestData.() -> Unit) {
        listener(LoginPluginRequestEvent::class.java) {
            if (channel == this.channel) {
                task(LoginPluginRequestData(messageId, data))
            }
        }
    }

    fun onPluginMessage(channel : String, task: ByteArray.() -> Unit) {
        listener(PluginMessageEvent::class.java) {
            if (channel == this.channel) {
                task(data)
            }
        }
    }

    data class LoginPluginRequestData(
        val id : Int,
        val data : ByteArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LoginPluginRequestData

            if (id != other.id) return false
            if (!data.contentEquals(other.data)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + data.contentHashCode()
            return result
        }
    }
}