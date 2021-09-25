package com.github.highright1234.glacier.protocol.packet.handshake.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class HandshakePacket(
    var protocolVersion : Int = 756,
    var serverAddress : String = "0.0.0.0",
    var serverPort : Int = 25565,
    var nextState : Int = 2
) : MinecraftPacket() {

    override fun write(buf: ByteBuf) {
        writeVarInt(protocolVersion, buf)
        writeString(serverAddress, buf, 255)
        buf.writeShort(serverPort)
        writeVarInt(nextState, buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        protocolVersion = readVarInt(buf)
        serverAddress = readString(buf, 255)
        serverPort = buf.readUnsignedShort()
        nextState = readVarInt(buf)
    }
}