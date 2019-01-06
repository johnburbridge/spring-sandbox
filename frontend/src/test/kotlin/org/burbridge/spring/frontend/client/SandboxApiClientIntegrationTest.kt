package org.burbridge.spring.frontend.client

import org.burbridge.spring.frontend.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.RestTemplate

class SandboxApiClientIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var sandboxApiClient: SandboxApiClient

    @Test
    fun `Can authenticate with API`() {
        val results = sandboxApiClient.getAllUsers()
        assertNotNull(results)
        assertTrue(results!!.total > 0)
    }
}