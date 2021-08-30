package com.github.highright1234.glacier.protocol;

import lombok.Getter;

import java.util.*;

@Getter
public class Protocol {

    public static Type HANDSHAKE = new Type();
    public static Type STATUS = new Type();
    public static Type LOGIN = new Type();
    public static Type PLAY = new Type();

    public static Set<Integer> SUPPORT_PROTOCOLS = new TreeSet<>();

    public static class Type {
        public DirectionData TO_CLIENT = new DirectionData();
        public DirectionData TO_SERVER = new DirectionData();

        public static class DirectionData {

            private final Map<Class<? extends AbstractPacket>, Map<Integer, Integer>> packetIdMap = new HashMap<>();
            private final Map<Integer, Map<Integer, AbstractPacket>> packetDataMap = new HashMap<>();

            public void addPacket(AbstractPacket packet, Map<Integer, Integer> mappingPacket) {
                packetIdMap.put(packet.getClass(), mappingPacket);
                for (int protocolVersion : mappingPacket.keySet()) {
                    packetDataMap.computeIfAbsent(protocolVersion, k -> new HashMap<>());
                    packetDataMap.get(protocolVersion).put(mappingPacket.get(protocolVersion), packet);
                }
            }

            public Integer getId(int protocolVersion, Class<? extends AbstractPacket> clazz) {
                return packetIdMap.get(clazz).get(protocolVersion);
            }

            public AbstractPacket getPacket(int protocolVersion, int id) {
                return packetDataMap.get(protocolVersion).get(id);
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
