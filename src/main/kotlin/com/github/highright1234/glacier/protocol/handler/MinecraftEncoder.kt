package com.github.highright1234.glacier.protocol.handler;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import com.github.highright1234.glacier.protocol.BufUtil;
import com.github.highright1234.glacier.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MinecraftEncoder extends MessageToByteEncoder<MinecraftPacket> {

    @Getter
    @Setter
    private Protocol.Type protocolType;

    private final boolean isServer;

    @Override
    protected void encode(ChannelHandlerContext ctx, MinecraftPacket msg, ByteBuf out) throws Exception {
        Protocol.Type.DirectionData dir = isServer ? protocolType.TO_CLIENT : protocolType.TO_SERVER;
        BufUtil.writeVarInt(dir.getId(msg.getClass()), out);
        msg.write(out);
    }
}