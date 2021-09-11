package com.github.highright1234.glacier.protocol.handler;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import com.github.highright1234.glacier.protocol.BufUtil;
import com.github.highright1234.glacier.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class MinecraftDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Getter
    @Setter
    private Protocol.Type protocolType;

    private boolean isServer;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int packetID = BufUtil.readVarInt(msg);
        Protocol.Type.DirectionData dir = isServer ? protocolType.TO_SERVER : protocolType.TO_CLIENT;
        MinecraftPacket packet = dir.getPacket(packetID);
        packet.read(msg);
        if (msg.isReadable()) {
            msg.release();
            return;
        }
        out.add(packet);
    }
}