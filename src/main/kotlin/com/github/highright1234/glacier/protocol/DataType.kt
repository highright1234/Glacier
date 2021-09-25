package com.github.highright1234.glacier.protocol;

import io.netty.buffer.ByteBuf;

public interface DataType {
    void write(ByteBuf buf);
    void read(ByteBuf buf);
}