package org.burbridge.sandbox.api

import org.burbridge.sandbox.api.config.InitialDataLoader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ActiveProfiles(value = ["default", "test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractSeededIntegrationTest {

    @Autowired
    lateinit var initialDataLoader: InitialDataLoader

    @BeforeEach
    fun configureDatabase() {
        initialDataLoader.initialize()
    }

    @AfterEach
    fun tearDownDatabase() {
        initialDataLoader.clean()
    }
}