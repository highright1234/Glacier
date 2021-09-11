package com.github.highright1234.glacier.event.event;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.event.ClientConnectionEvent;
import com.github.highright1234.glacier.protocol.packet.handshake.client.HandshakePacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ClientInitializeEvent extends ClientConnectionEvent {
    private final ClientConnection clientConnection;
    private final HandshakePacket handshakePacket;
}
