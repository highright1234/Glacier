package com.github.highright1234.glacier.packet.play.server

import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.readChat
import com.github.highright1234.glacier.protocol.writeChat
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component

data class PlayerListHeaderAndFooter(
    var header : Component = Component.empty(),
    var footer : Component = Component.empty()
) : MinecraftPacket() {
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeChat(header)
        buf.writeChat(footer)
    }

    override fun read(buf: ByteBuf, version: Int) {
        header = buf.readChat()
        footer = buf.readChat()
    }
}