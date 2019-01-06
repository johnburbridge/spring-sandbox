package org.burbridge.spring.frontend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate

@Configuration
@Profile("default")
class DefaultConfig {

    @Value("\${sandbox.api.uri}")
    lateinit var sandboxApiUri: String

    @Value("\${sandbox.user.username}")
    lateinit var sandboxApiUserUsername: String

    @Value("\${sandbox.user.password}")
    lateinit var sandboxApiUserPassword: String

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder()
            .basicAuthorization(sandboxApiUserUsername, sandboxApiUserPassword)
            .rootUri(sandboxApiUri)
            .build()
}
