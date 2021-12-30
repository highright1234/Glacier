package io.github.highright1234.glacier.event.event.server

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.ClientConnectionEvent

data class LoginPluginResponseEvent(
    override val clientConnection: io.github.highright1234.glacier.ClientConnection,
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