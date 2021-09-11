package com.github.highright1234.glacier.protocol.packet.status.server;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SLPResponse extends MinecraftPacket {

    private String jsonResponse;

    @Override
    public void write(ByteBuf buf) {
        writeString(jsonResponse, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        jsonResponse = readString(buf);
    }
}
