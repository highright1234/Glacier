package com.github.highright1234.glacier.packet.datatype

fun Byte.toVarLong() : VarLong {
    return VarLong(this.toLong())
}

fun Char.toVarLong() : VarLong {
    return VarLong(this.code.toLong())
}

fun Double.toVarLong() : VarLong {
    return VarLong(this.toLong())
}

fun Float.toVarLong() : VarLong {
    return VarLong(this.toLong())
}

fun Int.toVarLong() : VarLong {
    return VarLong(this.toLong())
}

fun Long.toVarLong() : VarLong {
    return VarLong(this)
}

fun Short.toVarLong() : VarLong {
    return VarLong(this.toLong())
}

data class VarLong(var value : Long) : Number() {

    override fun toByte(): Byte = value.toByte()

    override fun toChar(): Char = value.toInt().toChar()

    override fun toDouble(): Double = value.toDouble()

    override fun toFloat(): Float = value.toFloat()

    override fun toInt(): Int = value.toInt()

    override fun toLong(): Long = value

    override fun toShort(): Short = value.toShort()

}