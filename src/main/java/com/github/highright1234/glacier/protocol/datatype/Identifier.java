package com.github.highright1234.glacier.protocol.datatype;

import com.github.highright1234.glacier.protocol.BufUtil;
import com.github.highright1234.glacier.protocol.DataType;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Identifier implements DataType {

    private static final String NAME_SPACE_PATTERN = "^[a-z0-9-_]+$";
    private static final String NAME_PATTERN = "^[a-z0-9.-_\\\\]+$";
    @Getter
    private String nameSpace = "minecraft";
    @Getter
    private String name = "thing";

    public void setNameSpace(String value) throws Exception {
        if (!value.matches(NAME_SPACE_PATTERN)) {
            throw new Error("namespace is not matched pattern");
        }

        nameSpace = value;
    }

    public void setName(String value) {
        if (!value.matches(NAME_PATTERN)) {
            throw new Error("namespace is not matched pattern");
        }

        name = value;
    }

    @Override
    public void write(ByteBuf buf) {
        BufUtil.writeString(nameSpace+":"+name, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        String[] value = BufUtil.readString(buf).split(":");
        nameSpace = value[0];
        name = value[1];
    }

    @Override
    public String toString() {
        return nameSpace+":"+name;
    }
}
