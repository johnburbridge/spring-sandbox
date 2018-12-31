package org.burbridge.sandbox.api.config

//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.burbridge.sandbox.api.security.AppUserDetailsService
import org.burbridge.sandbox.api.security.AuthenticationSuccessHandlerImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@Profile("default")
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var successHandler: AuthenticationSuccessHandlerImpl

    @Autowired
    lateinit var userDetailsService: AppUserDetailsService

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**,/actuator/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }
}