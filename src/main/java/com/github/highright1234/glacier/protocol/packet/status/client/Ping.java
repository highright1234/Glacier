package com.github.highright1234.glacier.protocol.packet.status.client;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Ping extends AbstractPacket {

    private long payLoad;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {
        payLoad = buf.readLong();
    }
}
