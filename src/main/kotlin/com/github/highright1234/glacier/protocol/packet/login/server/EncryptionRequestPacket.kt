package com.github.highright1234.glacier.protocol.packet.login.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class EncryptionRequestPacket(
    var serverID: String = "",
    var publicKey : ByteArray = ByteArray(0),
    var verifyToken : ByteArray = ByteArray(0)
) : MinecraftPacket() {


    override fun write(buf: ByteBuf) {
        writeString(serverID, buf)
        writeVarInt(publicKey.size, buf)
        buf.writeBytes(publicKey)
        writeVarInt(verifyToken.size, buf)
        buf.writeBytes(verifyToken)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        serverID = readString(buf)
        val publicKeyLength: Int = readVarInt(buf)
        publicKey = buf.readBytes(publicKeyLength).array()
        val verifyTokenLength: Int = readVarInt(buf)
        verifyToken = buf.readBytes(verifyTokenLength).array()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptionRequestPacket

        if (serverID != other.serverID) return false
        if (!publicKey.contentEquals(other.publicKey)) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = serverID.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }
}