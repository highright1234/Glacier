package io.github.highright1234.glacier.builder.node

import io.github.highright1234.glacier.builder.GlacierBuilderDsl
import io.github.highright1234.glacier.event.Event
import io.github.highright1234.glacier.event.event.PacketReceivingEvent
import io.github.highright1234.glacier.event.event.PacketSendingEvent
import io.github.highright1234.glacier.protocol.MinecraftPacket

abstract class SettingNode : GlacierBuilderDsl {
    abstract fun <T : Event> listener(clazz : Class<T>, listener: Any)
    abstract fun <T : Event> listener(clazz : Class<T>, listener: T.() -> Unit)
    abstract fun <T : MinecraftPacket> receivingListener(clazz : Class<T>, listener: PacketReceivingEvent.() -> Unit)
    abstract fun <T : MinecraftPacket> sendingListener(clazz : Class<T>, listener: PacketSendingEvent.() -> Unit)
}