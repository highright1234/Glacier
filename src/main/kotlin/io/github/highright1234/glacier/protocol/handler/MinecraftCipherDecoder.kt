package io.github.highright1234.glacier.protocol.handler

import com.velocitypowered.natives.encryption.JavaVelocityCipher
import com.velocitypowered.natives.encryption.NativeVelocityCipher
import com.velocitypowered.natives.encryption.VelocityCipher
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import javax.crypto.SecretKey

class MinecraftCipherDecoder(native : Boolean, securityKey : SecretKey) : ByteToMessageDecoder() {
    private val cipherEncoder : VelocityCipher
    init {
        cipherEncoder = if (native) {
            NativeVelocityCipher.FACTORY.forDecryption(securityKey)
        } else {
            JavaVelocityCipher.FACTORY.forDecryption(securityKey)
        }
    }
    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {
        out += `in`.apply { cipherEncoder.process(this) }
    }
}