package com.github.highright1234.glacier.protocol.datatype

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.DataType
import net.kyori.adventure.nbt.BinaryTag

data class Slot(val itemID : Int = 0, val itemCount : Byte = 0, val binaryTag : BinaryTag) : DataType {

    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
    }
}