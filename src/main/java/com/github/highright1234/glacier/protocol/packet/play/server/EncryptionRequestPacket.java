package com.github.highright1234.glacier.protocol.packet.play.server;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EncryptionRequestPacket extends MinecraftPacket {

    private String serverID = "";
    private byte[] publicKey = new byte[0];
    private byte[] verifyToken = new byte[0];

    @Override
    public void write(ByteBuf buf) {
        writeString(serverID, buf);
        writeVarInt(publicKey.length, buf);
        buf.writeBytes(publicKey);
        writeVarInt(verifyToken.length, buf);
        buf.writeBytes(verifyToken);
    }

    @Override
    public void read(ByteBuf buf) {
        serverID = readString(buf);
        int publicKeyLength = readVarInt(buf);
        publicKey = buf.readBytes(publicKeyLength).array();
        int verifyTokenLength = readVarInt(buf);
        verifyToken = buf.readBytes(verifyTokenLength).array();
    }
}
