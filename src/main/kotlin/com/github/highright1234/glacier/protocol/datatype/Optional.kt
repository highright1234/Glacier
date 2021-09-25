package com.github.highright1234.glacier.protocol.datatype

import com.github.highright1234.glacier.protocol.DataType
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

class Optional<T : DataType?>(defaultPacket: T) : DataType {
    private val packet: T?
    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        if (packet == null) {
            buf.writeBoolean(false)
        } else {
            buf.writeBoolean(true)
            packet.write(buf)
        }
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        if (buf.readBoolean()) {
            packet!!.read(buf)
        }
    }

    @Throws(Exception::class)
    fun read(buf: ByteBuf, defaultPacket: T) {
        if (buf.readBoolean()) {
            defaultPacket!!.read(buf)
        }
    }

    companion object {
        private val NULL_PACKET: Exception = NullPointerException("packet must be not null if you use the read")
    }

    /**
     * use this if you are going to use read
     * @param defaultPacket default packet
     */
    init {
        packet = defaultPacket
    }
}