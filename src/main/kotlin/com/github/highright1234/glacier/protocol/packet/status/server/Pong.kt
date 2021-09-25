package com.github.highright1234.glacier.protocol.packet.status.server;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pong extends MinecraftPacket {

    private long payLoad;

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(payLoad);
    }

    @Override
    public void read(ByteBuf buf) {
        payLoad = buf.readLong();
    }
}
