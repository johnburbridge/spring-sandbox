package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.springsandbox.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
        return userRepository.findByUsername(username)
    }
}