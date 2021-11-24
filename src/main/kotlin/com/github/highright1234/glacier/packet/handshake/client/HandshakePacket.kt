package com.github.highright1234.glacier.packet.handshake.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class HandshakePacket(
    var protocolVersion : Int = 0,
    var serverAddress : String = "0.0.0.0",
    var serverPort : Int = 25565,
    var nextState : Int = 2
) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        writeVarInt(protocolVersion, buf)
        writeString(serverAddress, buf, 255)
        buf.writeShort(serverPort)
        writeVarInt(nextState, buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        protocolVersion = readVarInt(buf).toInt()
        serverAddress = readString(buf, 255)
        serverPort = buf.readUnsignedShort()
        nextState = readVarInt(buf).toInt()
    }
}