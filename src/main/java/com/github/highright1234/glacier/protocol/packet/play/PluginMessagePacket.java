package com.github.highright1234.glacier.protocol.packet.play;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import com.github.highright1234.glacier.protocol.datatype.Identifier;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginMessagePacket extends MinecraftPacket {

    private Identifier channel;
    private byte[] data;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {

    }
}
