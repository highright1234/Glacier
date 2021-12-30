package io.github.highright1234.glacier.packet.datatype

import io.github.highright1234.glacier.command.CommandSender
import io.github.highright1234.glacier.protocol.PacketDataType
import io.github.highright1234.glacier.protocol.writeString
import io.github.highright1234.glacier.protocol.writeVarInt
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.brigadier.tree.RootCommandNode
import io.netty.buffer.ByteBuf

data class CommandNodeData(
    var commandNodeData : CommandNode<CommandSender>
) : PacketDataType {
    override fun write(buf: ByteBuf) {
        val flags = getFlags(commandNodeData)
        buf.writeByte(flags)

        buf.writeVarInt(commandNodeData.children.size)
        commandNodeData.children.forEach {
            
        }

        if (commandNodeData is LiteralCommandNode) {
            buf.writeString(commandNodeData.name)
        } else if (commandNodeData is ArgumentCommandNode<*, *>) {
            if ((flags and 0x10) == 0x10) {

            }
        }
    }

    override fun read(buf: ByteBuf) {

    }

    private fun getFlags(commandNode : CommandNode<CommandSender>) : Int {
        var out = 0
        val nodeType = if (commandNode is RootCommandNode) 1 else if (commandNode is LiteralCommandNode) 2 else 3
        out += nodeType
        if (commandNode.redirect == null) out += 0x80
        if (commandNode is ArgumentCommandNode<*, *>) {
            if (commandNode.customSuggestions == null) out += 0x10
        }
        return out
    }
}