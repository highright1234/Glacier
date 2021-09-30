package com.github.highright1234.glacier.packet.datatype

import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import com.github.highright1234.glacier.protocol.BufUtil
import com.github.highright1234.glacier.protocol.PacketDataType

class EntityMetaPacketData : PacketDataType {
    private var index: Short = 0
    private val type = 0
    private var value: Any? = null
    @Throws(Exception::class)
    override fun write(buf: ByteBuf) {
        buf.writeByte(index.toInt())
        buf.writeByte(type)
        // TODO
    }

    @Throws(Exception::class)
    override fun read(buf: ByteBuf) {
        index = buf.readUnsignedByte()
        when (BufUtil.readVarInt(buf).value) {
            Type.BYTE -> value = buf.readByte()
            Type.VAR_INT, Type.DIRECTION, Type.POSE -> value = BufUtil.readVarInt(buf)
            Type.FLOAT -> value = buf.readFloat()
            Type.STRING -> value = BufUtil.readString(buf)
            Type.CHAT -> value = BufUtil.readChat(buf)
            Type.OPT_CHAT -> if (buf.readBoolean()) {
                value = BufUtil.readChat(buf)
            }
            Type.SLOT -> {
            }
            Type.BOOLEAN -> value = buf.readBoolean()
            Type.ROTATION -> {
                val rotation = Rotation()
                rotation.x = buf.readFloat()
                rotation.y = buf.readFloat()
                rotation.z = buf.readFloat()
                value = rotation
            }
            Type.POSITION -> {
                value = Position()
                (value as Position).read(buf)
            }
            Type.OPT_POSITION -> value = BufUtil.read(Optional(Position()), buf)
            Type.OPT_UUID -> if (buf.readBoolean()) {
                value = BufUtil.readUUID(buf)
            }
            Type.OPT_BLOCK_ID, Type.OPT_VAR_INT -> if (buf.readBoolean()) {
                value = BufUtil.readVarInt(buf)
            }
            Type.NBT -> {
            }
            Type.PARTICLE -> value = buf.readByte()
            Type.VILLAGER_DATA -> value = buf.readByte()
        }
    }

    object Type {
        const val BYTE = 0
        const val VAR_INT = 1
        const val FLOAT = 2
        const val STRING = 3
        const val CHAT = 4
        const val OPT_CHAT = 5
        const val SLOT = 6
        const val BOOLEAN = 7
        const val ROTATION = 8
        const val POSITION = 9
        const val OPT_POSITION = 10
        const val DIRECTION = 11
        const val OPT_UUID = 12
        const val OPT_BLOCK_ID = 13
        const val NBT = 14
        const val PARTICLE = 15
        const val VILLAGER_DATA = 16
        const val OPT_VAR_INT = 17
        const val POSE = 18
    }


    data class Rotation(
        var x: Float = 0f,
        var y: Float = 0f,
        var z: Float = 0f
    )

    object Pose {
        const val STANDING = 0
        const val FALL_FLYING = 1
        const val SLEEPING = 2
        const val SWIMMING = 3
        const val SPIN_ATTACK = 4
        const val DYING = 5
        const val LONG_JUMPING = 6
    }
}