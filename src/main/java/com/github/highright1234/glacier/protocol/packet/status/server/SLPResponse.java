package com.github.highright1234.glacier.protocol.packet.status.server;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SLPResponse extends AbstractPacket {

    private String jsonResponse;

    @Override
    public void write(ByteBuf buf) {
        writeString(jsonResponse, buf);
    }
}
