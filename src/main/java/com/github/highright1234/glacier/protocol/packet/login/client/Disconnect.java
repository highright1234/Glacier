package com.github.highright1234.glacier.protocol.packet.login.client;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

public class Disconnect extends AbstractPacket {

    @Getter
    @Setter
    private String reason;

    @Override
    public void write(ByteBuf buf) {
        writeChat(reason, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        reason = readChat(buf);
    }
}
