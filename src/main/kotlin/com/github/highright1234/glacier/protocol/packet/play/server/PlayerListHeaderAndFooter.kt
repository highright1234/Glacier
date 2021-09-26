package com.github.highright1234.glacier.protocol.packet.play.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.readChat
import com.github.highright1234.glacier.protocol.writeChat
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component

data class PlayerListHeaderAndFooter(
    var header : Component,
    var footer : Component
) : MinecraftPacket() {
    override fun write(buf: ByteBuf) {
        buf.writeChat(header)
        buf.writeChat(footer)
    }

    override fun read(buf: ByteBuf) {
        header = buf.readChat()
        footer = buf.readChat()
    }
}