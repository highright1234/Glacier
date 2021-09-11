package com.github.highright1234.glacier.protocol;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class BufUtil {
    public static int readVarInt(ByteBuf buf) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buf.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static long readVarLong(ByteBuf buf) {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = buf.readByte();
            long value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static String readString(ByteBuf buf) {
        return readString(buf, Short.MAX_VALUE);
    }

    public static String readChat(ByteBuf buf) {
        return readString(buf, 262144);
    }

    public static String readString(ByteBuf buf, int maxLength) {
        int length = readVarInt( buf );
        if ( length > maxLength * 4 ) {
            return null;
        }

        byte[] b = new byte[ length ];
        buf.readBytes( b );

        String s = new String( b, Charsets.UTF_8 );
        if ( s.length() > maxLength ) {
            return null;
        }

        return s;
    }

    public static UUID readUUID(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static Object read(Class<? extends DataType> clazz, ByteBuf buf) throws InstantiationException, IllegalAccessException {
        DataType out = clazz.newInstance();
        out.read(buf);
        return out;
    }

    public static void writeVarInt(int value, ByteBuf buf) {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                buf.writeByte(value);
                return;
            }
            buf.writeByte(value & 0x7F | 0x80);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public static void writeVarLong(long value, ByteBuf buf) {
        while (true) {
            if ((value & 0xFFFFFFFFFFFFFF80L) == 0) {
                buf.writeByte((int) value);
                return;
            }

            buf.writeByte((int) (value & 0x7F | 0x80));
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public static void writeString(String value, ByteBuf buf) {
        writeString(value, buf, Short.MAX_VALUE);
    }

    public static void writeChat(String value, ByteBuf buf) {
        writeString(value, buf, 262144);
    }

    public static void writeString(String value, ByteBuf buf, int maxLength) {
        if (value.length() > maxLength) {
            return;
        }
        writeVarInt(value.length(), buf);
        buf.writeBytes(value.getBytes());
    }

    public static void writeUUID(UUID value, ByteBuf buf) {
        buf.writeLong(value.getMostSignificantBits());
        buf.writeLong(value.getLeastSignificantBits());
    }

    public static void write(DataType object, ByteBuf buf) {
        object.write(buf);
    }
}
