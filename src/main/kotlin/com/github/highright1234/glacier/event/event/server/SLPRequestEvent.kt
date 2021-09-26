package com.github.highright1234.glacier.event.event.server

import com.github.highright1234.glacier.ClientConnection
import com.github.highright1234.glacier.event.ClientConnectionEvent
import com.github.highright1234.glacier.SLPResponseData

class SLPRequestEvent(
    override val clientConnection: ClientConnection,
    val slpResponseData: SLPResponseData
) : ClientConnectionEvent()