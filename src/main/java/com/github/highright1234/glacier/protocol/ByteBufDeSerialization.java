package com.github.highright1234.glacier.protocol;

import io.netty.buffer.ByteBuf;

public interface ByteBufDeSerialization {
    void deserialization(ByteBuf buf);
}