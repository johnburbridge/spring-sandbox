package org.burbridge.sandbox.api.service.core

import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun save(user: User): User {
        return userRepository.saveAndFlush(user)
    }
}