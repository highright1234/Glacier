package io.github.highright1234.glacier.command

interface TabCompleter {
    fun tabComplete(commandSender: CommandSender, vararg args : String)
}