package io.github.highright1234.glacier.event.event

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.Event
import java.util.*

data class JoiningCompleteEvent(
    var uuid : UUID,
    var userName : String,
    var client : io.github.highright1234.glacier.ClientConnection?
) : Event()