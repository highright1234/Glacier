package com.github.highright1234.glacier.packet.play.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.readChat
import com.github.highright1234.glacier.protocol.writeChat
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component

data class ActionBar(
    var message : Component = Component.empty()
) : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeChat(message)
    }

    override fun read(buf: ByteBuf, version: Int) {
        message = buf.readChat()
    }
}