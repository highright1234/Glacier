package com.github.highright1234.glacier.protocol.datatype;

import com.github.highright1234.glacier.protocol.DataType;
import com.github.highright1234.glacier.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Optional<T extends DataType> implements DataType {

    @Nullable
    private MinecraftPacket packet;

    private static final Exception NULL_PACKET = new NullPointerException("packet must be not null if you use the read");

    /**
     * use this if you are going to use read
     * @param defaultPacket default packet
     */
    public Optional(@NotNull MinecraftPacket defaultPacket) {
        this.packet = defaultPacket;
    }

    @Override
    public void write(ByteBuf buf) {
        if (packet == null) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            packet.write(buf);
        }
    }

    @Override
    public void read(ByteBuf buf) {
        if (buf.readBoolean()) {
            Objects.requireNonNull(packet).read(buf);
        }
    }

    public void read(ByteBuf buf, @NotNull T defaultPacket) {
        if (buf.readBoolean()) {
            defaultPacket.read(buf);
        }
    }
}
