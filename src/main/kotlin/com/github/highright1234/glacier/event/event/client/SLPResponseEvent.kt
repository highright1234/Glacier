package com.github.highright1234.glacier.event.event.client

import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.protocol.packet.status.server.SLPResponse

data class SLPResponseEvent(
    val slpResponsePacket : SLPResponse
) : Event()