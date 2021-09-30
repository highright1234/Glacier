package com.github.highright1234.glacier.builder.node

import com.github.highright1234.glacier.SLPResponseData
import com.github.highright1234.glacier.event.AbstractEventListener
import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.event.Listener
import com.github.highright1234.glacier.event.event.PacketReceivingEvent
import com.github.highright1234.glacier.event.event.PacketSendingEvent
import com.github.highright1234.glacier.event.event.PluginMessageEvent
import com.github.highright1234.glacier.event.event.client.JoiningCompleteEvent
import com.github.highright1234.glacier.event.event.client.LoginPluginRequestEvent
import com.github.highright1234.glacier.event.event.client.SLPResponseEvent
import com.github.highright1234.glacier.packet.datatype.Identifier
import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.collections.ArrayList

class ClientSettingNode : SettingNode() {

    val listeners = ArrayList<Listener>()

    override fun <T : Event> listener(clazz: Class<T>, listener: AbstractEventListener<T>) {
        listeners.add(listener)
    }

    override fun <T : Event> listener(clazz: Class<T>, listener: T.() -> Unit) {
        listener(clazz, object : AbstractEventListener<T>() {
            override fun listener(event: T) {
                listener(event)
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
            task(JoiningCompleteEvent(uuid, userName))
        }
    }

    fun onSLPResponseReceiving(task: SLPResponseData.() -> Unit) {
        listener(SLPResponseEvent::class.java) {
            task(slpResponsePacket.slpResponseData)
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