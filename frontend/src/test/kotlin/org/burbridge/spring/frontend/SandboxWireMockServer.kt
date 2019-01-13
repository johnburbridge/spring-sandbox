package org.burbridge.spring.frontend

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SandboxWireMockServer {

    @Value("\${sandbox.api.uri}")
    lateinit var sandboxApiUri: String

    @Value("\${wiremock.port}")
    lateinit var wireMockPort: String

    @Value("\${wiremock.record}")
    lateinit var wireMockRecord: String

    lateinit var wireMockServer: WireMockServer

    fun configureAndStart() {
        logger.info { "Starting wiremock on port $wireMockPort" }

        wireMockServer = WireMockServer(WireMockConfiguration.options()
                .port(wireMockPort.toInt())
        )
        wireMockServer.start()

        if (wireMockRecord.equals("true",true)) {
            logger.warn { "Proxying and recording requests for $sandboxApiUri" }
            wireMockServer.startRecording(sandboxApiUri)
        }

        configureFor("localhost", wireMockServer.port())

        if (wireMockRecord.equals("true",true))
            stubFor(proxyAllTo(sandboxApiUri).atPriority(1))
    }

    fun stop() {
        if (wireMockRecord.equals("true",true))
            wireMockServer.stopRecording()
        wireMockServer.stop()
    }
}