package com.github.highright1234.glacier.protocol.handler;

import com.github.highright1234.glacier.EventManager;
import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendingEventHandler extends ChannelOutboundHandlerAdapter {

    private final EventManager eventManager;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof MinecraftPacket) {
            eventManager.callEvent(msg);
        }
    }
}
