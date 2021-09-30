package com.github.highright1234.glacier.packet.datatype

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.PacketDataType
import com.github.highright1234.glacier.protocol.readString
import com.github.highright1234.glacier.protocol.writeString
import java.lang.Error

class Identifier : PacketDataType {

    companion object {

        private val NAME_SPACE_PATTERN = Regex("^[a-z0-9-_]+$")
        private val NAME_PATTERN = Regex("^[a-z0-9.-_\\\\]+$")

        @JvmStatic
        fun fromString(value : String) : Identifier {
            val out = Identifier()
            out.fromString(value)
            return out
        }
    }

    var nameSpace = "minecraft"
        @Throws(Exception::class)
        set(value) {
            if (!value.matches(NAME_SPACE_PATTERN)) {
                throw Error("namespace is not matched pattern")
            }
            field = value
        }

    var name = "thing"
    set(value) {
        @Throws(Exception::class)
        if (!value.matches(NAME_PATTERN)) {
            throw Error("namespace is not matched pattern")
        }
        field = value
    }

    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        buf.writeString("$nameSpace:$name")
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        fromString(buf.readString())
    }

    fun fromString(`in` : String) {
        val value: Array<String> = `in`.split(":").toTypedArray()
        nameSpace = value[0]
        name = value[1]
    }

    override fun toString(): String {
        return "$nameSpace:$name"
    }
}