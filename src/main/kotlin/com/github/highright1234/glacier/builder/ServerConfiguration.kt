package com.github.highright1234.glacier.builder

data class ServerConfiguration(
    var onlineMode : Boolean = false,
    var connectionTimeout : Long = 5000
)
