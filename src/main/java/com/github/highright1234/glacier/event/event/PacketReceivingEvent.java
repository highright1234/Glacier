package com.github.highright1234.glacier.event.event;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.event.Cancellable;
import com.github.highright1234.glacier.event.ClientConnectionEvent;
import com.github.highright1234.glacier.protocol.MinecraftPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class PacketReceivingEvent extends ClientConnectionEvent implements Cancellable {

    private final ClientConnection clientConnection;
    @Setter
    private boolean cancelled;
    private final MinecraftPacket packet;

}
