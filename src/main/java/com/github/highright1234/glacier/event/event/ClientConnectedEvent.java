package com.github.highright1234.glacier.event.event;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.event.ClientConnectionEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientConnectedEvent extends ClientConnectionEvent {
    @Getter
    private final ClientConnection clientConnection;
}
