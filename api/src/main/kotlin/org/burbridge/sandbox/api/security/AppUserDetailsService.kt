package org.burbridge.sandbox.api.security

import mu.KotlinLogging
import org.burbridge.sandbox.api.service.core.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Transactional
@Service("userDetailsService")
class AppUserDetailsService(
        @Autowired
        val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        logger.info { "Loading UserDetails for $username" }
        val user = userService.findByEmail(username) ?:
            throw UsernameNotFoundException("No user found with username: $username")

        return AppUserPrincipal(user)
    }
}