package com.github.highright1234.glacier.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder

class CommandManager {

    val dispatcher = CommandDispatcher<CommandSender>()
    private val brigadierUtil = BrigadierUtil(this)

    fun execute(input : String, executor : CommandSender) {
        dispatcher.execute(input, executor)
    }

    fun registerCommand(command: Command) {
        dispatcher.register(brigadierUtil.convert(command))
    }

    fun registerCommand(command: LiteralArgumentBuilder<CommandSender>) {
        dispatcher.register(command)
    }
}