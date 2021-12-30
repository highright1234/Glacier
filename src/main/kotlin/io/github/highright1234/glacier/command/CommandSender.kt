package io.github.highright1234.glacier.command

abstract class CommandSender {
    abstract fun runCommand(input : String)
    abstract fun tabComplete(input : String)
}