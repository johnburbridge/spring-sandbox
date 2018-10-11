package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.springsandbox.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/users/{username}")
    fun getUserByUsername(@PathVariable("username") username: String): User {
        return userRepository.findByUsername(username)
    }
}