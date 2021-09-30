package com.github.highright1234.glacier.packet.play.server

import com.github.highright1234.glacier.protocol.*
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import java.util.*

data class ServerChatMessage(
    var data : Component = Component.empty(),
    var position : Byte = 0,
    var sender : UUID = UUID(0, 0)
) : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeChat(data)
        buf.writeByte(position.toInt())
        buf.writeUUID(sender)
    }

    override fun read(buf: ByteBuf, version: Int) {
        data = buf.readChat()
        position = buf.readByte()
        sender = buf.readUUID()
    }
}