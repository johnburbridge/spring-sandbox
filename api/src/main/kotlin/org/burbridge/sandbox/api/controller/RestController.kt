package org.burbridge.sandbox.api.controller

import org.burbridge.springsandbox.domain.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {

    @GetMapping("/users/{user}")
    fun getUser(@PathVariable("user") user: String): User {
        return User(id = 0L, firstName = "Tommy", lastName = "Cat", username = "tommythecat")
    }
}