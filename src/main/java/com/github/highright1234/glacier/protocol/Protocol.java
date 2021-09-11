package com.github.highright1234.glacier.protocol;

import com.github.highright1234.glacier.GlacierServer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class Protocol {

    private final GlacierServer server;

    public Protocol() {
        server = null;
    }

    public Protocol( @NotNull GlacierServer server ) {
        this.server = server;
    }

    public final Type HANDSHAKE = new Type();
    public final Type STATUS = new Type();
    public final Type LOGIN = new Type();
    public final Type PLAY = new Type();

    public static class Type {
        public DirectionData TO_CLIENT = new DirectionData();
        public DirectionData TO_SERVER = new DirectionData();

        public static class DirectionData {

            private final Map<Class<? extends MinecraftPacket>, Integer> packetIdMap = new HashMap<>();
            private final Map<Integer, MinecraftPacket> packetDataMap = new HashMap<>();

            public void addPacket(MinecraftPacket packet, int id) {
                packetIdMap.put(packet.getClass(), id);
                packetDataMap.put(id, packet);
            }

            public Integer getId(Class<? extends MinecraftPacket> clazz) {
                return packetIdMap.get(clazz);
            }

            public MinecraftPacket getPacket(int id) {
                return packetDataMap.get(id);
            }

            public MinecraftPacket getPacket(Class<? extends MinecraftPacket> clazz) {
                return packetDataMap.get(getId(clazz));
            }
        }
    }

    public static class Version {
        public static final int MINECRAFT_1_7_5 = 4;
        public static final int MINECRAFT_1_7_10 = 5;
        public static final int MINECRAFT_1_8 = 47;
        public static final int MINECRAFT_1_9 = 107;
        public static final int MINECRAFT_1_9_1 = 108;
        public static final int MINECRAFT_1_9_2 = 109;
        public static final int MINECRAFT_1_9_4 = 110;
        public static final int MINECRAFT_1_10 = 210;
        public static final int MINECRAFT_1_11 = 315;
        public static final int MINECRAFT_1_11_1 = 316;
        public static final int MINECRAFT_1_12 = 335;
        public static final int MINECRAFT_1_12_1 = 338;
        public static final int MINECRAFT_1_12_2 = 340;
        public static final int MINECRAFT_1_13 = 393;
        public static final int MINECRAFT_1_13_1 = 401;
        public static final int MINECRAFT_1_13_2 = 404;
        public static final int MINECRAFT_1_14 = 477;
        public static final int MINECRAFT_1_14_1 = 480;
        public static final int MINECRAFT_1_14_2 = 485;
        public static final int MINECRAFT_1_14_3 = 490;
        public static final int MINECRAFT_1_14_4 = 498;
        public static final int MINECRAFT_1_15 = 573;
        public static final int MINECRAFT_1_15_1 = 575;
        public static final int MINECRAFT_1_15_2 = 578;
        public static final int MINECRAFT_1_16 = 735;
        public static final int MINECRAFT_1_16_1 = 736;
        public static final int MINECRAFT_1_16_2 = 751;
        public static final int MINECRAFT_1_16_3 = 753;
        public static final int MINECRAFT_1_16_4 = 754;
        public static final int MINECRAFT_1_17 = 755;
        public static final int MINECRAFT_1_17_1 = 756;
    }
}
