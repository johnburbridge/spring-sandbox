package org.burbridge.sandbox.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

@Configuration
@Profile("development")
@PropertySource("development.properties")
class DevelopmentConfig
