package io.github.highright1234.glacier.packet.login.server

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.packet.datatype.Identifier
import io.netty.buffer.ByteBuf
import java.lang.Exception

data class LoginPluginRequest(
    var messageID: Int,
    var channel : Identifier,
    var data : ByteArray
) : MinecraftPacket() {
    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {

    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginPluginRequest

        if (messageID != other.messageID) return false
        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageID
        result = 31 * result + channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}