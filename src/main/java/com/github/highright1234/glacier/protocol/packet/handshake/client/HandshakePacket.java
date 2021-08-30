package com.github.highright1234.glacier.protocol.packet.handshake.client;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HandshakePacket extends AbstractPacket {

    private int protocolVersion = 756;
    private String serverAddress = "0.0.0.0";
    private int serverPort = 25565;
    private int nextState = 2;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {
        protocolVersion = readVarInt(buf);
        serverAddress = readString0(buf, 255);
        serverPort = buf.readUnsignedShort();
        nextState = readVarInt(buf);
    }
}
