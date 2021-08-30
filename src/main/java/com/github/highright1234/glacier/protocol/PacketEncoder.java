package com.github.highright1234.glacier.protocol;

import com.github.highright1234.glacier.ClientConnection;
import com.github.highright1234.glacier.EventManager;
import com.github.highright1234.glacier.event.event.PacketReceivingEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {

    private final ClientConnection cliCon;

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
        if (EventManager.getInstance().callEvent(new PacketReceivingEvent(cliCon, msg)).isCancelled()) {
            out.release();
            return;
        }
        BufUtil.writeVarInt(
                cliCon.getProtocolType().TO_CLIENT.getId(cliCon.getProtocolVersion(), msg.getClass()),
                out
        );
        msg.write(out);
    }
}