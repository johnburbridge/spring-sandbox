package org.burbridge.spring.frontend

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractIntegrationTest {

    @Autowired
    lateinit var sandboxWireMockServer: SandboxWireMockServer

    @BeforeAll
    fun configureSystemUnderTest() {
        sandboxWireMockServer.configureAndStart()
    }

    @AfterAll
    fun tearDown() {
        sandboxWireMockServer.stop()
    }
}