package com.github.highright1234.glacier.event.event.server

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent

data class LoginPluginResponseEvent(
    override val clientConnection: ClientConnection,
    val successful: Boolean,
    val data : ByteArray
) : ClientConnectionEvent() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginPluginResponseEvent

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