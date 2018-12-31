package org.burbridge.sandbox.api.config

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.test.context.TestPropertySource

@Configuration
@Profile("test")
@TestPropertySource("test.properties")
class TestConfig {

    @Bean
    fun restTemplate() = TestRestTemplate()
}
