package com.github.highright1234.glacier.protocol.handler;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.protocol.MinecraftPacket;
import com.github.highright1234.glacier.protocol.packet.handshake.client.HandshakePacket;
import com.github.highright1234.glacier.protocol.packet.status.client.Ping;
import com.github.highright1234.glacier.protocol.packet.status.client.SLPRequest;
import com.github.highright1234.glacier.protocol.packet.status.server.Pong;
import com.github.highright1234.glacier.protocol.packet.status.server.SLPResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class InitializeHandler extends ChannelInboundHandlerAdapter {

    private final ClientConnection clientConnection;
    private State state;
    private boolean isLogin = false;
    private final static Exception WRONG_PACKET = new RuntimeException("it's wrong packet");
    private final static Exception WRONG_NEXT_STATE = new RuntimeException("it's wrong next state!");
    private final static Exception WRONG_STATE_VALUE = new RuntimeException("wrong state value!");
    private final static Exception NULL_PING = new IllegalStateException("you did not send the ping");
    private Ping ping = null;

    public boolean isServer() {
        return clientConnection != null;
    }

    public InitializeHandler(@NotNull ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        state = State.HANDSHAKE;
    }

    public InitializeHandler() {
        this.clientConnection = null;
        state = State.LOGIN;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MinecraftPacket)) {
            ctx.fireChannelRead(msg);
            return;
        }
        if (clientConnection != null) {
            switch (state) {
                case HANDSHAKE:
                    HandshakePacket packet = (HandshakePacket) msg;
                    clientConnection.setProtocolVersion(packet.getProtocolVersion());
                    if (packet.getNextState() == 1) {
                        state = State.STATUS;
                    } else if (packet.getNextState() == 2) {
                        state = State.LOGIN;
                    } else {
                        throw WRONG_NEXT_STATE;
                    }
                    break;
                case STATUS:
                    if (msg instanceof SLPRequest) {
                        // TODO
                    } else {
                        throw WRONG_PACKET;
                    }
                    break;
                case PING:
                    if (msg instanceof Ping) {
                        clientConnection.sendPacket(new Pong(ping.getPayLoad()));
                    } else {
                        throw WRONG_PACKET;
                    }
                    break;
            }
        } else {
            switch (state) {
                case HANDSHAKE:
                    throw WRONG_STATE_VALUE;
                case STATUS:
                    SLPResponse slpResponse = (SLPResponse) msg;
                    // TODO
                    break;
                case PING:
                    if (ping == null) {
                        throw NULL_PING;
                    }
                    Pong pong = (Pong) msg;
                    if (ping.getPayLoad() == pong.getPayLoad()) {
                        // TODO
                    }
                    break;
                case LOGIN:
                    // TODO
                    break;
            }
        }
    }

    public enum State {
        HANDSHAKE,
        STATUS,
        PING,
        LOGIN,
        ENCRYPTION,
        LOGIN_SUCCESSFUL
    }
}
