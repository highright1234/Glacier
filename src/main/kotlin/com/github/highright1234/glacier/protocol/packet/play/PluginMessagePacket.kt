package com.github.highright1234.glacier.protocol.packet.play

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import com.github.highright1234.glacier.protocol.datatype.Identifier

class PluginMessagePacket(
    var channel : Identifier = Identifier(),
    var data : ByteArray = ByteArray(0)
) : MinecraftPacket() {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        writeIdentifier(channel, buf)
        buf.writeShort(data.size)
        buf.writeBytes(data)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        channel = Identifier()
        channel.read(buf)
        val length = buf.readShort()
        buf.readBytes(length.toInt())
    }
}