package com.github.highright1234.glacier.packet.datatype

fun Byte.toVarInt() : VarInt {
    return VarInt(this.toInt())
}

fun Char.toVarInt() : VarInt {
    return VarInt(this.code)
}

fun Double.toVarInt() : VarInt {
    return VarInt(this.toInt())
}

fun Float.toVarInt() : VarInt {
    return VarInt(this.toInt())
}

fun Int.toVarInt() : VarInt {
    return VarInt(this)
}

fun Long.toVarInt() : VarInt {
    return VarInt(this.toInt())
}

fun Short.toVarInt() : VarInt {
    return VarInt(this.toInt())
}

data class VarInt(var value : Int) : Number() {

    override fun toByte(): Byte = value.toByte()

    override fun toChar(): Char = value.toChar()

    override fun toDouble(): Double = value.toDouble()

    override fun toFloat(): Float = value.toFloat()

    override fun toInt(): Int = value

    override fun toLong(): Long = value.toLong()

    override fun toShort(): Short = value.toShort()

}