package org.burbridge.spring.frontend.client

import org.burbridge.spring.frontend.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus


class SandboxApiClientIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var sandboxApiClient: SandboxApiClient

    @Test
    fun `Can authenticate with API`() {
        val results = sandboxApiClient.authenticate("test@metabuild.org","test")
        assertNotNull(results)
        assertEquals(HttpStatus.OK, results.statusCode)
        assertEquals(listOf("ROLE_USER"), results.body)
    }
}