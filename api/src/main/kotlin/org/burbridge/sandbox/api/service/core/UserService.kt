package org.burbridge.sandbox.api.service.core

import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.burbridge.spring.common.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun save(userDto: UserDto): User {
        return save(toEntity(userDto))
    }

    fun save(user: User): User {
        return userRepository.saveAndFlush(user)
    }

    fun toEntity(userDto: UserDto): User {
        return User(id = userDto.id!!.toLong(),
                email = userDto.email!!,
                password = userDto.password!!,
                firstName = userDto.firstName!!,
                lastName = userDto.lastName!!,
                enabled = userDto.enabled
        )
    }
}