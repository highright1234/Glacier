package com.github.highright1234.glacier.packet.play

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.readString
import com.github.highright1234.glacier.protocol.writeString

class PluginMessagePacket(
    var channel : String = "",
    var data : ByteArray = ByteArray(0)
) : MinecraftPacket() {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf, version: Int) {
        buf.writeString(channel)
        buf.writeShort(data.size)
        buf.writeBytes(data)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        channel = buf.readString()
        val length = buf.readShort()
        data = buf.readBytes(length.toInt()).array()
    }
}