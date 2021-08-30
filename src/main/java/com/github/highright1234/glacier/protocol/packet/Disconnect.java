package com.github.highright1234.glacier.protocol.packet;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Disconnect extends AbstractPacket {

    private String reason;

    @Override
    public void write(ByteBuf buf) {
        writeString(reason, buf);
    }
}
