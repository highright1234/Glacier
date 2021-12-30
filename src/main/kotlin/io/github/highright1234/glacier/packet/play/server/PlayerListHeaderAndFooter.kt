package io.github.highright1234.glacier.packet.play.server

import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.protocol.readChat
import io.github.highright1234.glacier.protocol.writeChat
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