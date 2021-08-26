package com.github.highright1234.glacier.protocol;

import java.util.HashMap;
import java.util.Map;

public class Protocol {

    public static final Map<Class<? extends AbstractPacket>, Direction> CLASS_DIRECTION_DATA = new HashMap<>();
    public static final Map<Integer, Map<Integer, Class<? extends AbstractPacket>>> PACKET_DATA = new HashMap<>();

    public static void addPacket(Class<? extends AbstractPacket> clazz, Map<Integer, Integer> mappingPacket,Direction direction) {
        CLASS_DIRECTION_DATA.put(clazz, direction);
        for (int protocolVersion : mappingPacket.keySet()) {
            PACKET_DATA.get(protocolVersion).put(mappingPacket.get(protocolVersion), clazz);
        }
    }

    public enum Direction {
        TO_CLIENT, TO_SERVER
    }

    public static class Version {
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
