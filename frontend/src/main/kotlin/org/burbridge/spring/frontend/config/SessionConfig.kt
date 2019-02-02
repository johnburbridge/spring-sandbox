package org.burbridge.spring.frontend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer

@Profile("!test")
@Configuration
@EnableRedisHttpSession
class SessionConfig : AbstractHttpSessionApplicationInitializer() {

    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }
}