package io.github.highright1234.glacier.event.event.server

import io.github.highright1234.glacier.ClientConnection
import io.github.highright1234.glacier.event.ClientConnectionEvent
import io.github.highright1234.glacier.SLPResponseData

class SLPRequestEvent(
    override val clientConnection: io.github.highright1234.glacier.ClientConnection,
    val slpResponseData: SLPResponseData
) : ClientConnectionEvent()