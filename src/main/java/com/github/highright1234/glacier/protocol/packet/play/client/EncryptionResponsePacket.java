package com.github.highright1234.glacier.protocol.packet.play.client;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EncryptionResponsePacket extends MinecraftPacket {

    private byte[] sharedSecret = new byte[0];
    private byte[] verifyToken = new byte[0];

    @Override
    public void write(ByteBuf buf) {
        writeVarInt(sharedSecret.length, buf);
        buf.writeBytes(sharedSecret);
        writeVarInt(verifyToken.length, buf);
        buf.writeBytes(verifyToken);
    }

    @Override
    public void read(ByteBuf buf) {
        int sharedSecretLength = readVarInt(buf);
        sharedSecret = buf.readBytes(sharedSecretLength).array();
        int verifyTokenLength = readVarInt(buf);
        verifyToken = buf.readBytes(verifyTokenLength).array();
    }
}
