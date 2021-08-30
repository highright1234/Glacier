package com.github.highright1234.glacier.protocol;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.EventManager;
import com.github.highright1234.glacier.event.event.PacketReceivingEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final ClientConnection cliCon;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int packetID = BufUtil.readVarInt(msg);
        AbstractPacket packet = cliCon.getProtocolType().TO_SERVER.getPacket(cliCon.getProtocolVersion(), packetID);
        packet.read(msg);
        if (EventManager.getInstance().callEvent(new PacketReceivingEvent(cliCon, packet)).isCancelled()) {
            msg.release();
            return;
        }
        if (msg.isReadable()) {
            msg.release();
            return;
        }
        out.add(packet);
    }
}