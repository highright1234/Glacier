package io.github.highright1234.glacier.protocol

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import java.util.UUID
import io.github.highright1234.glacier.packet.datatype.Identifier
import com.google.common.base.Charsets
import kotlin.jvm.JvmOverloads
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.Component
import kotlin.RuntimeException
import kotlin.experimental.and

fun ByteBuf.writeVarInt(value : Int) {
    BufUtil.writeVarInt(value, this)
}

fun ByteBuf.writeVarLong(value : Long) {
    BufUtil.writeVarLong(value, this)
}

fun ByteBuf.readVarInt(): Int {
    return BufUtil.readVarInt(this)
}


fun ByteBuf.readVarLong(): Long {
    return BufUtil.readVarLong(this)
}

@Throws(Exception::class)
fun ByteBuf.writeIdentifier(value : Identifier) {
    BufUtil.writeIdentifier(value, this)
}

@Throws(Exception::class)
fun ByteBuf.readIdentifier(): Identifier {
    return BufUtil.readIdentifier(this)
}

@Throws(Exception::class)
fun ByteBuf.writeString(value : String, maxLength: Int = Short.MAX_VALUE.toInt()) {
    BufUtil.writeString(value, this, maxLength)
}

@Throws(Exception::class)
fun ByteBuf.readString(maxLength: Int = Short.MAX_VALUE.toInt()): String {
    return BufUtil.readString(this, maxLength)
}

@Throws(Exception::class)
fun ByteBuf.writeChat(value : Component) {
    BufUtil.writeChat(value, this)
}

@Throws(Exception::class)
fun ByteBuf.readChat() : Component {
    return BufUtil.readChat(this)
}

fun ByteBuf.writeUUID(value : UUID) {
    BufUtil.writeUUID(value, this)
}

fun ByteBuf.readUUID() : UUID {
    return BufUtil.readUUID(this)
}

@Throws(Exception::class)
fun ByteBuf.read(out: PacketDataType): Any {
    out.read(this)
    return out
}

@Throws(Exception::class)
fun ByteBuf.write(out: PacketDataType) {
    out.write(this)
}

infix fun Byte.and(other: Int): Int {
    return this.toInt() and other
}


open class BufUtil {
    companion object {

        private val OUT_OF_MAX_LENGTH: Exception = RuntimeException("string length is out of max length")

        @JvmStatic
        fun readVarInt(buf: ByteBuf): Int {
            var numRead = 0
            var result = 0
            var read: Byte
            do {
                read = buf.readByte()
                val value: Byte = read and 127
                result = result or (value.toInt() shl 7 * numRead)
                numRead++
                if (numRead > 5) {
                    throw RuntimeException("VarInt is too big")
                }
            } while (read.toInt() and 128 != 0)
            return result
        }

        @JvmStatic
        fun readVarLong(buf: ByteBuf): Long {
            var numRead = 0
            var result: Long = 0
            var read: Byte
            do {
                read = buf.readByte()
                val value: Long = (read and 127).toLong()
                result = result or (value shl 7 * numRead)
                numRead++
                if (numRead > 10) {
                    throw RuntimeException("VarLong is too big")
                }
            } while (read.toInt() and 128 != 0)
            return result
        }

        @JvmStatic
        @Throws(Exception::class)
        fun readIdentifier(buf: ByteBuf): Identifier {
            val identifier = Identifier()
            identifier.read(buf)
            return identifier
        }

        @JvmStatic
        @JvmOverloads
        @Throws(Exception::class)
        fun readString(buf: ByteBuf, maxLength: Int = Short.MAX_VALUE.toInt()): String {
            val length = readVarInt(buf)
            if (length > maxLength * 4) {
                throw OUT_OF_MAX_LENGTH
            }
            val b = ByteArray(length)
            buf.readBytes(b)
            val s = String(b, Charsets.UTF_8)
            if (s.length > maxLength) {
                throw OUT_OF_MAX_LENGTH
            }
            return s
        }

        @JvmStatic
        @Throws(Exception::class)
        fun readChat(buf: ByteBuf): Component {
            return GsonComponentSerializer.gson().deserialize(readString(buf, 262144))
        }

        @JvmStatic
        fun readUUID(buf: ByteBuf): UUID {
            return UUID(buf.readLong(), buf.readLong())
        }

        @JvmStatic
        @Throws(Exception::class)
        fun read(value: Any, buf: ByteBuf): Any {
            return when(value) {
                is PacketDataType -> value.read(buf)
                is String -> buf.readString()
                is Component -> buf.readChat()
                is UUID -> buf.readUUID()
                is Number -> throw RuntimeException("write isn't support number")
                else -> throw RuntimeException("unsupported type")
            }
        }

        @JvmStatic
        fun writeVarInt(input: Int, buf: ByteBuf) {
            var value = input
            while (true) {
                if (value and -0x80 == 0) {
                    buf.writeByte(value)
                    return
                }
                buf.writeByte(value and 0x7F or 0x80)
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value = value ushr 7
            }
        }

        @JvmStatic
        fun writeVarLong(input: Long, buf: ByteBuf) {
            var value = input
            while (true) {
                if (value and -0x80L == 0L) {
                    buf.writeByte(value.toInt())
                    return
                }
                buf.writeByte((value and 0x7F or 0x80).toInt())
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value = value ushr 7
            }
        }

        @JvmStatic
        fun writeChat(component: Component?, buf: ByteBuf) {
            writeString(GsonComponentSerializer.gson().serialize(component!!), buf, 262144)
        }

        @JvmStatic
        @Throws(Exception::class)
        fun writeIdentifier(value: Identifier?, buf: ByteBuf) {
            value!!.write(buf)
        }

        @JvmStatic
        @JvmOverloads
        fun writeString(value: String?, buf: ByteBuf, maxLength: Int = Short.MAX_VALUE.toInt()) {
            if (value!!.length > maxLength) {
                return
            }
            writeVarInt(value.length, buf)
            buf.writeBytes(value.toByteArray())
        }

        @JvmStatic
        fun writeUUID(value: UUID, buf: ByteBuf) {
            buf.writeLong(value.mostSignificantBits)
            buf.writeLong(value.leastSignificantBits)
        }

        @JvmStatic
        @Throws(Exception::class)
        fun write(value : Any, buf: ByteBuf) {
            when(value) {
                is PacketDataType -> value.write(buf)
                is String -> buf.writeString(value)
                is Component -> buf.writeChat(value)
                is UUID -> buf.writeUUID(value)
                is Number -> throw RuntimeException("write isn't support number")
                else -> throw RuntimeException("unsupported type")
            }
        }
    }
}