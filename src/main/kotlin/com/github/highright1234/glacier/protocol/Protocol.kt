package com.github.highright1234.glacier.protocol

import java.util.HashMap
import java.lang.Error
import com.github.highright1234.glacier.protocol.packet.handshake.client.HandshakePacket
import com.github.highright1234.glacier.protocol.packet.status.client.SLPRequest
import com.github.highright1234.glacier.protocol.packet.status.client.Ping
import com.github.highright1234.glacier.protocol.packet.status.server.SLPResponse
import com.github.highright1234.glacier.protocol.packet.status.server.Pong
import com.github.highright1234.glacier.protocol.packet.login.client.LoginStart
import com.github.highright1234.glacier.protocol.packet.login.server.LoginSuccess
import com.github.highright1234.glacier.protocol.packet.DisconnectPacket
import com.github.highright1234.glacier.protocol.packet.login.server.EncryptionRequestPacket
import com.github.highright1234.glacier.protocol.packet.login.client.EncryptionResponsePacket
import com.github.highright1234.glacier.protocol.packet.play.PluginMessagePacket

class Protocol {

    val HANDSHAKE = Type()
    val STATUS = Type()
    val LOGIN = Type()
    val PLAY = Type()

    class Type {
        @JvmField
        var TO_CLIENT = DirectionData()
        @JvmField
        var TO_SERVER = DirectionData()

        class DirectionData {
            private val packetIdMap: MutableMap<Class<out MinecraftPacket>, Int?> = HashMap()
            private val packetDataMap: MutableMap<Int?, MinecraftPacket> = HashMap()
            fun addPacket(packet: MinecraftPacket, id: Int) {
                if (packetIdMap[packet.javaClass] != null) {
                    packetDataMap.remove(packetIdMap[packet.javaClass])
                }
                packetIdMap[packet.javaClass] = id
                packetDataMap[id] = packet
            }

            fun getId(clazz: Class<out MinecraftPacket>): Int? {
                return packetIdMap[clazz]
            }

            fun getPacket(id: Int): MinecraftPacket? {
                return packetDataMap[id]
            }

            fun getPacket(clazz: Class<out MinecraftPacket>): MinecraftPacket? {
                return packetDataMap[getId(clazz)]
            }
        }
    }

    object Version {
        // It's same protocol version of 1.7 - 1.7.5
        const val MINECRAFT_1_7_5 = 4

        // It's same protocol version of 1.7.6 - 1.7.10
        const val MINECRAFT_1_7_10 = 5

        // It's same protocol version of 1.8 - 1.8.9
        const val MINECRAFT_1_8_9 = 47
        const val MINECRAFT_1_9 = 107
        const val MINECRAFT_1_9_1 = 108
        const val MINECRAFT_1_9_2 = 109

        // It's same protocol version of 1.9.3 - 1.9.4
        const val MINECRAFT_1_9_4 = 110

        // It's same protocol version of 1.10 - 1.10.2
        const val MINECRAFT_1_10 = 210
        const val MINECRAFT_1_11 = 315
        const val MINECRAFT_1_11_1 = 316
        const val MINECRAFT_1_12 = 335
        const val MINECRAFT_1_12_1 = 338
        const val MINECRAFT_1_12_2 = 340
        const val MINECRAFT_1_13 = 393
        const val MINECRAFT_1_13_1 = 401
        const val MINECRAFT_1_13_2 = 404
        const val MINECRAFT_1_14 = 477
        const val MINECRAFT_1_14_1 = 480
        const val MINECRAFT_1_14_2 = 485
        const val MINECRAFT_1_14_3 = 490
        const val MINECRAFT_1_14_4 = 498
        const val MINECRAFT_1_15 = 573
        const val MINECRAFT_1_15_1 = 575
        const val MINECRAFT_1_15_2 = 578
        const val MINECRAFT_1_16 = 735
        const val MINECRAFT_1_16_1 = 736
        const val MINECRAFT_1_16_2 = 751
        const val MINECRAFT_1_16_3 = 753
        const val MINECRAFT_1_16_4 = 754
        const val MINECRAFT_1_17 = 755
        const val MINECRAFT_1_17_1 = 756
    }

    companion object {
        fun createProtocol(version: Int): Protocol {
            if (version < Version.MINECRAFT_1_7_5 || version > Version.MINECRAFT_1_17_1) {
                throw Error("version is unsupported version")
            }
            val out = Protocol()
            out.HANDSHAKE.TO_SERVER.addPacket(HandshakePacket(), 0x00)
            out.STATUS.TO_SERVER.addPacket(SLPRequest(), 0x00)
            out.STATUS.TO_SERVER.addPacket(Ping(), 0x00)
            out.STATUS.TO_CLIENT.addPacket(SLPResponse(""), 0x00)
            out.STATUS.TO_CLIENT.addPacket(Pong(), 0x00)
            out.LOGIN.TO_SERVER.addPacket(LoginStart(), 0x00)
            out.LOGIN.TO_CLIENT.addPacket(LoginSuccess(), 0x02)
            out.LOGIN.TO_CLIENT.addPacket(DisconnectPacket(), 0x00)
            out.LOGIN.TO_SERVER.addPacket(EncryptionRequestPacket(), 0x01)
            out.LOGIN.TO_CLIENT.addPacket(EncryptionResponsePacket(), 0x01)
            out.PLAY.TO_CLIENT.addPacket(PluginMessagePacket(), 0x3F)
            out.PLAY.TO_SERVER.addPacket(PluginMessagePacket(), 0x17)
            if (version >= Version.MINECRAFT_1_7_10) {
                if (version >= Version.MINECRAFT_1_8_9) {
                    if (version >= Version.MINECRAFT_1_9) {
                        if (version >= Version.MINECRAFT_1_9_1) {
                            if (version >= Version.MINECRAFT_1_9_2) {
                                if (version >= Version.MINECRAFT_1_9_4) {
                                    if (version >= Version.MINECRAFT_1_10) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return out
        }
    }
}