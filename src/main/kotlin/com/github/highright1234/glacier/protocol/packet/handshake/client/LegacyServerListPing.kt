package com.github.highright1234.glacier.protocol.packet.handshake.client;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data
class LegacyServerListPing extends MinecraftPacket {

    private short payload;

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(payload);
    }

    @Override
    public void read(ByteBuf buf) {
        payload = buf.readUnsignedByte();
    }
}
