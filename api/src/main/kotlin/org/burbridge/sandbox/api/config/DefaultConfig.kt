package org.burbridge.sandbox.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EntityScan("org.burbridge.springsandbox.domain")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class DefaultConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAware<String> { Optional.empty() }
}