package org.burbridge.spring.frontend.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer

private val logger = KotlinLogging.logger {}

@Profile("!test")
@Configuration
@EnableRedisHttpSession
class SessionConfig : AbstractHttpSessionApplicationInitializer() {

    @Value("\${spring.redis.host}")
    lateinit var springRedisHost: String

    @Value("\${spring.redis.port}")
    lateinit var springRedisPort: String

    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        logger.info { "Connecting to redis server $springRedisHost:$springRedisPort" }
        return LettuceConnectionFactory(springRedisHost, springRedisPort.toInt())
    }
}