package io.github.highright1234.glacier.packet.login.server

import io.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import java.util.UUID

data class LoginSuccess(
    var uuid: UUID = UUID(0, 0),
    var userName: String = ""
) : MinecraftPacket() {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {
        // it is not mistake
        writeString(uuid.toString(), buf)
        writeString(userName, buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        // it is not mistake
        uuid = UUID.fromString(readString(buf))
        userName = readString(buf)
    }
}