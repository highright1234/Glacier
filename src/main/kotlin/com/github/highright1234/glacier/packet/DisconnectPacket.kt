package com.github.highright1234.glacier.packet

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text

data class DisconnectPacket(var reason : Component = text("kicked by operator")) : MinecraftPacket() {
    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {
        writeChat(reason, buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        reason = readChat(buf)
    }
}