package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.error.RecordNotFoundException
import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.springsandbox.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
