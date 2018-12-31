package org.burbridge.sandbox.api.security

import mu.KotlinLogging
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service("userDetailsService")
@Transactional
class AppUserDetailsService(
        @Autowired
        val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        logger.info { "Loading UserDetails for $username" }
        val user = userRepository.findByEmail(username) ?:
            throw UsernameNotFoundException("No user found with username: $username")

        return AppUserPrincipal(user)
    }
}