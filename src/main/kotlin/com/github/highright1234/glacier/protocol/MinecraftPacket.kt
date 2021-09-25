package com.github.highright1234.glacier.protocol;

import io.netty.buffer.ByteBuf;

public abstract class MinecraftPacket extends BufUtil {

    public abstract void write(ByteBuf buf);

    public abstract void read(ByteBuf buf);

}