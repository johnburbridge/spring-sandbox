package org.burbridge.spring.frontend.security

import org.burbridge.spring.frontend.client.SandboxApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class RestAuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {

    @Autowired
    lateinit var sandboxApiClient: SandboxApiClient

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails,
                                                authentication: UsernamePasswordAuthenticationToken) {
        if (authentication.credentials == null) {
            logger.debug("Authentication failed: no credentials provided")

            throw BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"))
        }

        val presentedPassword = authentication.credentials.toString()

        if (!passwordEncoder.matches(presentedPassword, userDetails.password)) {
            logger.debug("Authentication failed: password does not match stored value")

            throw BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"))
        }
    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(name: String, auth: UsernamePasswordAuthenticationToken): UserDetails {
        val password = auth.credentials.toString()
        var loadedUser: UserDetails?
        try {
            val authenticationResponse = sandboxApiClient.authenticate(name, password)
            if (authenticationResponse.statusCode.value() == 401) {
                return User("wrongUsername", "wrongPass", emptyList())
            }
            val roles = authenticationResponse.body.orEmpty()
            val authorities = roles.map {
                role -> SimpleGrantedAuthority(role)
            }
            loadedUser = User(name, passwordEncoder.encode(password), authorities)
        } catch (ex: Exception) {
            throw AuthenticationServiceException("Could not retrieve Principal [$name] from API", ex)
        }

        return loadedUser
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}