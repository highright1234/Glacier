package io.github.highright1234.glacier.packet.login.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.protocol.readVarInt
import io.netty.buffer.ByteBuf

data class LoginPluginResponse(
    var messageID : Int,
    var successful : Boolean,
    var data : ByteArray
) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {

    }

    override fun read(buf: ByteBuf, version: Int) {
        messageID = buf.readVarInt()
        successful = buf.readBoolean()

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginPluginResponse

        if (messageID != other.messageID) return false
        if (successful != other.successful) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageID.hashCode()
        result = 31 * result + successful.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}