package com.github.highright1234.glacier.event;

import com.github.highright1234.glacier.ClientConnection;

public abstract class ClientConnectionEvent extends Event {
    public abstract ClientConnection getClientConnection();
}
