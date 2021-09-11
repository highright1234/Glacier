package com.github.highright1234.glacier.protocol.handler;

import com.github.highright1234.glacier.EventManager;
import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReceivingEventHandler extends ChannelInboundHandlerAdapter {

    private final EventManager eventManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MinecraftPacket) {
            eventManager.callEvent(msg);
        }
    }
}
