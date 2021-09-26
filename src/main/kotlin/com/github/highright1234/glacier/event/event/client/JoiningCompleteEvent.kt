package com.github.highright1234.glacier.event.event.client

import com.github.highright1234.glacier.event.Event
import java.util.*

data class JoiningCompleteEvent(
    var uuid : UUID,
    var userName : String
) : Event()