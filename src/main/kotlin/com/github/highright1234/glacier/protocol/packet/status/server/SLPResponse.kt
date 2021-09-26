package com.github.highright1234.glacier.protocol.packet.status.server

import com.github.highright1234.glacier.SLPResponseData
import com.github.highright1234.glacier.protocol.MinecraftPacket
import com.github.highright1234.glacier.protocol.readString
import com.google.gson.Gson
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class SLPResponse(var slpResponseData : SLPResponseData) : MinecraftPacket() {

    override fun write(buf: ByteBuf) {
        writeString(Gson().toJson(slpResponseData), buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        slpResponseData = Gson().fromJson(buf.readString(), SLPResponseData::class.java)
    }
}