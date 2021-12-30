package io.github.highright1234.glacier.protocol.handler

import com.velocitypowered.natives.encryption.JavaVelocityCipher
import com.velocitypowered.natives.encryption.NativeVelocityCipher
import com.velocitypowered.natives.encryption.VelocityCipher
import com.velocitypowered.natives.encryption.VelocityCipherFactory
import kotlin.Throws
import java.lang.Exception
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import javax.crypto.SecretKey

class MinecraftCipherEncoder(native : Boolean, securityKey : SecretKey) : MessageToByteEncoder<ByteBuf>() {
    private val cipherEncoder : VelocityCipher
    init {
        cipherEncoder = if (native) {
            NativeVelocityCipher.FACTORY.forEncryption(securityKey)
        } else {
            JavaVelocityCipher.FACTORY.forEncryption(securityKey)
        }
    }
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        out.writeBytes(msg.apply { cipherEncoder.process(this) })
    }
}