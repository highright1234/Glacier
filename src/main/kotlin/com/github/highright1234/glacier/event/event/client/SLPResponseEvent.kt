package com.github.highright1234.glacier.event.event.client

import com.github.highright1234.glacier.event.Event
import com.github.highright1234.glacier.packet.status.server.SLPResponse

data class SLPResponseEvent(
    val slpResponsePacket : SLPResponse,
    val ping : Long
) : Event()