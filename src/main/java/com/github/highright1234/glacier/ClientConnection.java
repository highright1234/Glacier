package com.github.highright1234.glacier;

import com.github.highright1234.glacier.protocol.MinecraftPacket;
import com.github.highright1234.glacier.protocol.Protocol;
import io.netty.channel.Channel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
@ToString
public class ClientConnection {

    @NotNull
    private final Channel ch;
    @Getter
    @Setter
    private int protocolVersion = Protocol.Version.MINECRAFT_1_7_5;
    @Setter
    @NotNull
    private Protocol.Type protocolType;

    public ClientConnection(@NotNull Channel ch, @NotNull Protocol.Type protocolType) {
        this.ch = ch;
        this.protocolType = protocolType;
    }

    public void sendPacket(@NotNull MinecraftPacket packet) {
        if (ch.isActive()) {
            ch.writeAndFlush(packet);
        }
    }

    public void disconnect() {

    }
}
