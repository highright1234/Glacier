package com.github.highright1234.glacier.command

abstract class Command(val commandName : String, vararg alias : String) {
    abstract fun execute(commandSender: CommandSender, vararg args : String)
}