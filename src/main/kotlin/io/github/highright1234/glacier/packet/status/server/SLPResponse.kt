package io.github.highright1234.glacier.packet.status.server

import io.github.highright1234.glacier.SLPResponseData
import io.github.highright1234.glacier.protocol.MinecraftPacket
import io.github.highright1234.glacier.protocol.readString
import com.google.gson.Gson
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf

data class SLPResponse(var slpResponseData : SLPResponseData = SLPResponseData()) : MinecraftPacket() {

    override fun write(buf: ByteBuf, version: Int) {
        writeString(Gson().toJson(slpResponseData), buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf, version: Int) {
        slpResponseData = Gson().fromJson(buf.readString(), SLPResponseData::class.java)
    }
}