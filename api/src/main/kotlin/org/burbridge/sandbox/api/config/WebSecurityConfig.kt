package org.burbridge.sandbox.api.config

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableOAuth2Sso
@Profile("default")
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                .antMatchers("/actuator/**")
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
    }
}