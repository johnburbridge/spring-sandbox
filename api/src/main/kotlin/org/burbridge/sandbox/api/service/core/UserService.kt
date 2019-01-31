package org.burbridge.sandbox.api.service.core

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.repository.core.RoleRepository
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun createWithDefaultRole(user: User) = createWithRole(user, DEFAULT_ROLE)

    fun createWithRole(user: User, roleName: String): User {
        val role = roleRepository.findByName(roleName)
        when (role != null) {
            true -> user.roles.add(role)
            false -> logger.warn { "Could not find role $roleName" }
        }
        return userRepository.saveAndFlush(user)
    }

    fun save(user: User): User {
        return userRepository.saveAndFlush(user)
    }

    fun updateLastLogin(date: Date) {
        userRepository.updateLastLogin(date)
    }

    companion object {
        const val DEFAULT_ROLE = "ROLE_USER"
    }
}
