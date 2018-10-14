package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.error.RecordNotFoundException
import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.sandbox.api.domain.User
import org.burbridge.spring.common.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(path = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/")
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): User {
        try {
            return userRepository.findByUsername(username)
        } catch (exception: EmptyResultDataAccessException) {
            logger.error("Could not find user: $username", exception)
            throw RecordNotFoundException(username)
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto): User {
        return userRepository.saveAndFlush(
                User(id = 0L,
                    username = userDto.username!!,
                    firstName = userDto.firstName!!,
                    lastName = userDto.lastName!!
                )
        )
    }
}
