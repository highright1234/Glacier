package com.github.highright1234.glacier.protocol.packet.play.server

import com.github.highright1234.glacier.protocol.*
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import java.util.*

data class ChatMessage(
    var data : Component,
    var position : Byte = 0,
    var sender : UUID = UUID(0, 0)
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeChat(data)
        buf.writeByte(position.toInt())
        buf.writeUUID(sender)
    }

    override fun read(buf: ByteBuf) {
        data = buf.readChat()
        position = buf.readByte()
        sender = buf.readUUID()
    }
}