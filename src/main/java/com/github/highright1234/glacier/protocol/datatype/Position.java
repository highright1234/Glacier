package com.github.highright1234.glacier.protocol.datatype;

import com.github.highright1234.glacier.protocol.ByteBufDeSerialization;
import com.github.highright1234.glacier.protocol.ByteBufSerialization;
import io.netty.buffer.ByteBuf;
import lombok.Data;

public @Data class Position implements ByteBufSerialization, ByteBufDeSerialization {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Position() {
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void serialization(ByteBuf buf) {
        buf.writeLong(((long) (x & 0x3FFFFFF) << 38) | ((long) (z & 0x3FFFFFF) << 12) | (y & 0xFFF));
    }

    @Override
    public void deserialization(ByteBuf buf) {
        long val = buf.readLong();
        x = (int) (val >> 38);
        y = (int) ((val >> 26) & 0xFFF);
        z = (int) (val << 38 >> 38);
    }
}
