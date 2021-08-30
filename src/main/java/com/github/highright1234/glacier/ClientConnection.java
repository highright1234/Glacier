package com.github.highright1234.glacier;

import com.github.highright1234.glacier.protocol.AbstractPacket;
import com.github.highright1234.glacier.protocol.Protocol;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ClientConnection {

    private final Channel ch;
    @Setter
    private Protocol.Type protocolType = Protocol.HANDSHAKE;
    @Setter
    private int protocolVersion;

    public void sendPacket(AbstractPacket packet) {
        if (ch.isActive()) {
            ch.writeAndFlush(packet);
        }
    }

    public void disconnect() {

    }

    public ClientConnection(Channel ch, int protocolVersion) {
        this.ch = ch;
        this.protocolVersion = protocolVersion;
    }
}
