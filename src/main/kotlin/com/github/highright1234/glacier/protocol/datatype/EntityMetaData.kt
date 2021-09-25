package com.github.highright1234.glacier.protocol.datatype;

import com.github.highright1234.glacier.protocol.BufUtil;
import com.github.highright1234.glacier.protocol.DataType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

public @Data class EntityMetaData implements DataType {

    private short index;
    private int type;
    private Object value;

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(index);
        buf.writeByte(type);
        // TODO
    }

    @Override
    public void read(ByteBuf buf) {
        index = buf.readUnsignedByte();
        switch (BufUtil.readVarInt(buf)) {
            case Type.BYTE:
                value = buf.readByte();
                break;
            case Type.VAR_INT:
                value = BufUtil.readVarInt(buf);
                break;
            case Type.FLOAT:
                value = buf.readFloat();
                break;
            case Type.STRING:
                value = BufUtil.readString(buf);
                break;
            case Type.CHAT:
                value = BufUtil.readChat(buf);
                break;
            case Type.OPT_CHAT:
                value = buf.readByte();
                break;
            case Type.SLOT:
                // TODO
                break;
            case Type.BOOLEAN:
                value = buf.readBoolean();
                break;
            case Type.ROTATION:
                value = buf.readByte();
                break;
            case Type.POSITION:
                value = new Position();
                ((Position) value).read(buf);
                break;
            case Type.OPT_POSITION:
                value = buf.readByte();
                break;
            case Type.DIRECTION:
                value = buf.readByte();
                break;
            case Type.OPT_UUID:
                value = buf.readByte();
                break;
            case Type.OPT_BLOCK_ID:
                value = buf.readByte();
                break;
            case Type.NBT:
//                value = ;
                break;
            case Type.PARTICLE:
                value = buf.readByte();
                break;
            case Type.VILLAGER_DATA:
                value = buf.readByte();
                break;
            case Type.OPT_VAR_INT:
                value = buf.readByte();
                break;
            case Type.POSE:
                value = buf.readByte();
                break;
        }
    }

    public static class Type {
        public static final int BYTE = 0;
        public static final int VAR_INT = 1;
        public static final int FLOAT = 2;
        public static final int STRING = 3;
        public static final int CHAT = 4;
        public static final int OPT_CHAT = 5;
        public static final int SLOT = 6;
        public static final int BOOLEAN = 7;
        public static final int ROTATION = 8;
        public static final int POSITION = 9;
        public static final int OPT_POSITION = 10;
        public static final int DIRECTION = 11;
        public static final int OPT_UUID = 12;
        public static final int OPT_BLOCK_ID = 13;
        public static final int NBT = 14;
        public static final int PARTICLE = 15;
        public static final int VILLAGER_DATA = 16;
        public static final int OPT_VAR_INT = 17;
        public static final int POSE = 18;
    }
    public enum Pose {

        STANDING(0),
        FALL_FLYING(1),
        SLEEPING(2),
        SWIMMING(3),
        SPIN_ATTACK(4),
        SNEAKING(5),
        DYING(6);

        private int x;

        Pose(int x) {
        }

        public int getValue() {
            return x;
        }
    }
}
