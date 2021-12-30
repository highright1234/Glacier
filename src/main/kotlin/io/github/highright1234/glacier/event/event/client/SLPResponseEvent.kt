package io.github.highright1234.glacier.event.event.client

import io.github.highright1234.glacier.event.Event
import io.github.highright1234.glacier.packet.status.server.SLPResponse

data class SLPResponseEvent(
    val slpResponsePacket : SLPResponse,
    val ping : Long
) : Event()