package org.burbridge.sandbox.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.util.*

@Configuration
@EnableWebSecurity
@EntityScan("org.burbridge.sandbox.api.domain")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Profile("default")
class DefaultConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAware<String> { Optional.empty() }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
    }
}