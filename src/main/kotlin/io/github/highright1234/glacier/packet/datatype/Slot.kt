package io.github.highright1234.glacier.packet.datatype

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.github.highright1234.glacier.protocol.PacketDataType
import net.kyori.adventure.nbt.BinaryTag

data class Slot(val itemID : Int = 0, val itemCount : Byte = 0, val binaryTag : BinaryTag) : PacketDataType {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
    }
}