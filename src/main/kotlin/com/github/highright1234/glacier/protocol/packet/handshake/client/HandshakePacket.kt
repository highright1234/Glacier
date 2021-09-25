package com.github.highright1234.glacier.protocol.packet.handshake.client;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HandshakePacket extends MinecraftPacket {

    private int protocolVersion = 756;
    private String serverAddress = "0.0.0.0";
    private int serverPort = 25565;
    private int nextState = 2;

    @Override
    public void write(ByteBuf buf) {
        writeVarInt(protocolVersion, buf);
        writeString(serverAddress, buf, 255);
        buf.writeShort(serverPort);
        writeVarInt(nextState, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        protocolVersion = readVarInt(buf);
        serverAddress = readString(buf, 255);
        serverPort = buf.readUnsignedShort();
        nextState = readVarInt(buf);
    }
}
