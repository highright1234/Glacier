package com.github.highright1234.glacier.protocol.datatype

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.DataType
import com.github.highright1234.glacier.protocol.readString
import com.github.highright1234.glacier.protocol.writeString
import java.lang.Error

class Identifier : DataType {

    private var nameSpace = "minecraft"

    private var name = "thing"
    @Throws(Exception::class)
    fun setNameSpace(value: String) {
        if (!value.matches(NAME_SPACE_PATTERN)) {
            throw Error("namespace is not matched pattern")
        }
        nameSpace = value
    }

    fun setName(value: String) {
        if (!value.matches(NAME_PATTERN)) {
            throw Error("namespace is not matched pattern")
        }
        name = value
    }

    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        buf.writeString("$nameSpace:$name")
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        val value: Array<String> = buf.readString().split(":").toTypedArray()
        setNameSpace(value[0])
        setName(value[1])
    }

    override fun toString(): String {
        return "$nameSpace:$name"
    }

    companion object {
        private val NAME_SPACE_PATTERN = Regex("^[a-z0-9-_]+$")
        private val NAME_PATTERN = Regex("^[a-z0-9.-_\\\\]+$")
    }
}