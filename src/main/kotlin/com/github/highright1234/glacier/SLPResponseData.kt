package com.github.highright1234.glacier

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import java.util.UUID

class SLPResponseData {

    private val version: Version = Version()
    private val players: Players = Players()
    private val description: Component = text("Glacier server")
    private var favicon: String = icon

    var icon: String
        get() = favicon.substring(0, 22)
        set(value) {
            favicon = "data:image/png;base64,$value"
        }

    data class Version(val name : String = "IDK", val protocol : Int = 0)

    data class Players(val max : Int = 0, val online : Int = 0, val sample : List<Sample> = ArrayList()) {
        data class Sample(val name : String = "HighRight", val id : UUID = UUID(0, 0))
    }
}