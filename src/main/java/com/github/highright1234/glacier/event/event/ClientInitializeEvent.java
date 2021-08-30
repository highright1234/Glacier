package com.github.highright1234.glacier.event.event;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.event.ClientConnectionEvent;

public class ClientInitializeEvent extends ClientConnectionEvent {
    @Override
    public ClientConnection getClientConnection() {
        return null;
    }
}
