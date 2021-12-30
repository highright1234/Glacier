package io.github.highright1234.glacier.builder

data class ClientConfiguration(
    var userName : String = "",
    var password : String = "",
    var connectionTimeout : Long = 5000
)