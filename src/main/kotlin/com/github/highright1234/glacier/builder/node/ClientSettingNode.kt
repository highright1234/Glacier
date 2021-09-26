package com.github.highright1234.glacier.builder.node

import com.github.highright1234.glacier.SLPResponseData
import com.github.highright1234.glacier.event.AbstractEventListener
import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.event.Listener
import com.github.highright1234.glacier.event.event.PluginMessageEvent
import com.github.highright1234.glacier.event.event.client.JoiningCompleteEvent
import com.github.highright1234.glacier.event.event.client.LoginPluginRequestEvent
import com.github.highright1234.glacier.event.event.client.SLPResponseEvent
import com.github.highright1234.glacier.protocol.datatype.Identifier
import java.util.*
import kotlin.collections.ArrayList

class ClientSettingNode : SettingNode() {

    val listeners = ArrayList<Listener>();

    fun <T : Event>listener(clazz: Class<T>, listener: AbstractEventListener<T>) {
        listeners.add(listener)
    }

    fun onJoiningComplete(task : (UUID, String) -> Unit) {
        listener(JoiningCompleteEvent::class.java) {
            task.invoke(it.uuid, it.userName)
        }
    }

    fun onSLPResponseReceiving(task: (SLPResponseData) -> Unit) {
        listener(SLPResponseEvent::class.java) {
            task.invoke(it.slpResponsePacket.slpResponseData)
        }
    }

    fun onLoginPluginRequest(channel : Identifier, task: (Int, ByteArray) -> Unit) {
        listener(LoginPluginRequestEvent::class.java) {
            if (channel == it.channel) {
                task.invoke(it.messageId, it.data)
            }
        }
    }

    fun onPluginMessage(channel : String, task: (ByteArray) -> Unit) {
        listener(PluginMessageEvent::class.java) {
            if (channel == it.channel) {
                task.invoke(it.data)
            }
        }
    }
}