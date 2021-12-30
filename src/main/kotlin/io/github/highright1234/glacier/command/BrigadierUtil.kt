package io.github.highright1234.glacier.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal


class BrigadierUtil(commandManager: CommandManager) {

    private val commandManager : CommandManager

    init {
        this.commandManager = commandManager
    }

    fun convert(command : Command) : LiteralArgumentBuilder<CommandSender> {
        return literal<CommandSender>(command.commandName)
            .executes { context ->
                command.execute(context.source, context.input)
                1
            }
    }
}