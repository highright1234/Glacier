package com.github.highright1234.glacier.protocol;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public abstract class AbstractPacket extends BufUtil {

    public abstract void read(ByteBuf buf);

    public abstract void write(ByteBuf buf);

    public static Object read(ByteBuf buf, Class<? extends ByteBufSerialization> clazz) {
        // TODO
        return null;
    }
}
