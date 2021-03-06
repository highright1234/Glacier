package io.github.highright1234.glacier.packet.datatype

import io.netty.buffer.ByteBuf
import io.github.highright1234.glacier.protocol.PacketDataType

data class Position(var x : Int = 0, var y : Int = 0, var z: Int = 0) : PacketDataType {

    override fun write(buf: ByteBuf) {
        buf.writeLong((x and 0x3FFFFFF).toLong() shl 38 or ((z and 0x3FFFFFF).toLong() shl 12) or (y and 0xFFF).toLong())
    }

    override fun read(buf: ByteBuf) {
        val `val` = buf.readLong()
        x = (`val` shr 38).toInt()
        y = (`val` shr 26 and 0xFFF).toInt()
        z = (`val` shl 38 shr 38).toInt()
    }
}