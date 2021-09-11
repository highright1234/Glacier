package com.github.highright1234.glacier.protocol.packet;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DisconnectPacket extends MinecraftPacket {

    private String reason;

    @Override
    public void write(ByteBuf buf) {
        writeString(reason, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        reason = readString(buf);
    }
}
