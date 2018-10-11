package org.burbridge.sandbox.api

import org.burbridge.sandbox.api.config.DevelopmentDataInitializer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractSeededIntegrationTest {

    @Autowired
    lateinit var developmentDataInitializer: DevelopmentDataInitializer

    @BeforeEach
    fun configureDatabase() {
        developmentDataInitializer.initialize()
    }

    @AfterEach
    fun tearDownDatabase() {
        developmentDataInitializer.clean()
    }
}