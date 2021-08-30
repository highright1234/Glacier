package com.github.highright1234.glacier.protocol.packet.status.server;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Pong extends AbstractPacket {

    private long payLoad;

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(payLoad);
    }
}
