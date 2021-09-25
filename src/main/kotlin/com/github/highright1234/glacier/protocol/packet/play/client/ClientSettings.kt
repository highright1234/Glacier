package com.github.highright1234.glacier.protocol.packet.play.client

import com.github.highright1234.glacier.protocol.MinecraftPacket
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.DataType
import com.github.highright1234.glacier.protocol.and

class ClientSettings : MinecraftPacket() {
    private var locale: String? = ""
    private var viewDistance: Byte = 0
    private var chatMode: Byte = 0
    private var chatColors = false
    private val displayedSkinParts = DisplayedSkinParts()
    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        writeString(locale, buf)
        buf.writeByte(viewDistance.toInt())
        buf.writeByte(chatMode.toInt())
        buf.writeBoolean(chatColors)
        displayedSkinParts.write(buf)
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        locale = readString(buf)
        viewDistance = buf.readByte()
        chatMode = buf.readByte()
        chatColors = buf.readBoolean()
        displayedSkinParts.read(buf)
    }

    data class DisplayedSkinParts(
        var cape : Boolean= false,
        var jacket: Boolean = false,
        var sleeve: Boolean = false,
        var rightSleeve: Boolean = false,
        var leftPantsLeg: Boolean = false,
        var rightPantsLeg: Boolean = false,
        private var hat: Boolean = true
    ) : DataType {

        @Throws(Exception::class)
        override fun write(buf: ByteBuf) {
            var out = 0
            if (cape) out += 0x01
            if (jacket) out += 0x02
            if (sleeve) out += 0x04
            if (rightSleeve) out += 0x08
            if (leftPantsLeg) out += 0x10
            if (rightPantsLeg) out += 0x20
            if (hat) out += 0x40
            buf.writeByte(out)
        }

        @Throws(Exception::class)
        override fun read(buf: ByteBuf) {
            val `in` = buf.readByte()
            if (`in` and 0x01 == 0x01) cape = true
            if (`in` and 0x02 == 0x02) jacket = true
            if (`in` and 0x04 == 0x04) sleeve = true
            if (`in` and 0x08 == 0x08) rightSleeve = true
            if (`in` and 0x10 == 0x10) leftPantsLeg = true
            if (`in` and 0x20 == 0x20) rightPantsLeg = true
            if (`in` and 0x40 == 0x40) hat = true
        }
    }
}