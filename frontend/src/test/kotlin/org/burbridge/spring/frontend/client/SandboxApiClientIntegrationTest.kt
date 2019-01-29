package org.burbridge.spring.frontend.client

import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.frontend.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class SandboxApiClientIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var sandboxApiClient: SandboxApiClient

    @Test
    fun `Can authenticate with API server`() {
        val results = sandboxApiClient.authenticate("test@metabuild.org","test")
        assertNotNull(results)
        assertEquals(HttpStatus.OK, results.statusCode)
        assertEquals(listOf("ROLE_USER"), results.body)
    }

    @Test
    fun `Can retain basic authentication`() {
        val results = sandboxApiClient.authenticate("test@metabuild.org","test")
        assertNotNull(results)
        assertEquals(HttpStatus.OK, results.statusCode)
        assertEquals(listOf("ROLE_USER"), results.body)

        val moreResults = sandboxApiClient.getAllUsers()
        assertNotNull(moreResults)
        assertEquals(2, moreResults?.total)
    }

    @Test
    fun `Cannot access a protected resource with wrong role`() {
        val results = sandboxApiClient.authenticate("test@metabuild.org","test")
        assertNotNull(results)
        assertEquals(HttpStatus.OK, results.statusCode)
        assertEquals(listOf("ROLE_USER"), results.body)

        val e = assertThrows(HttpClientErrorException::class.java) {
            sandboxApiClient.createUser(UserDto(
                    id = 100,
                    firstName = "First",
                    lastName = "Last",
                    password = "s3cr37",
                    email = "firstLast@metabuild.org",
                    enabled = true))
        }
        assertEquals("403 Forbidden", e.message)
    }
}