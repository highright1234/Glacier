package io.github.highright1234.glacier.packet.login.client

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.netty.buffer.ByteBuf

data class EncryptionResponsePacket(
    var sharedSecret: ByteArray = ByteArray(0),
    var verifyToken: ByteArray = ByteArray(0)
) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        writeVarInt(sharedSecret.size, buf)
        buf.writeBytes(sharedSecret)
        writeVarInt(verifyToken.size, buf)
        buf.writeBytes(verifyToken)
    }

    override fun read(buf: ByteBuf, version: Int) {
        val sharedSecretLength: Int = readVarInt(buf)
        sharedSecret = buf.readBytes(sharedSecretLength).array()
        val verifyTokenLength: Int = readVarInt(buf)
        verifyToken = buf.readBytes(verifyTokenLength).array()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptionResponsePacket

        if (!sharedSecret.contentEquals(other.sharedSecret)) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sharedSecret.contentHashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }
}