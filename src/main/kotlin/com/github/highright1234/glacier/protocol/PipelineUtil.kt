package com.github.highright1234.glacier.protocol;

import com.github.highright1234.glacier.ClientConnection;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.jetbrains.annotations.Nullable;

public class PipelineUtil {

    public static final AttributeKey<ClientConnection> CONNECTION = AttributeKey.newInstance( "CLIENT_CONNECTION" );
    public static final String MINECRAFT_ENCODER = "minecraft-encoder";
    public static final String MINECRAFT_DECODER = "minecraft-decoder";

    public static final String PACKET_ENCODER = "packet-encoder";
    public static final String PACKET_DECODER = "packet-decoder";

    public static final String RECEIVING_EVENT_CALLER = "receiving-event-caller";
    public static final String SENDING_EVENT_CALLER = "sending-event-caller";

    @Nullable
    public static ClientConnection getClientConnection(Channel ch) {
        return ch.attr(CONNECTION).get();
    }

}
