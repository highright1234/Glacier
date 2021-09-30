package com.github.highright1234.glacier.event.event.client

import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.packet.datatype.Identifier

data class LoginPluginRequestEvent(
    val messageId : Int,
    val channel : Identifier,
    val data : ByteArray
) : Event() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginPluginRequestEvent

        if (messageId != other.messageId) return false
        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageId
        result = 31 * result + channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

}