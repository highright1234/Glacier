package com.github.highright1234.glacier.event.event

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent

data class PluginMessageEvent(
    override val clientConnection: ClientConnection?,
    val channel : String,
    val data : ByteArray
    ) : ClientConnectionEvent() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginMessageEvent

        if (clientConnection != other.clientConnection) return false
        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clientConnection?.hashCode() ?: 0
        result = 31 * result + channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}