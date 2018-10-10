package org.burbridge.sandbox.api
import org.burbridge.springsandbox.domain.User
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class SandboxApi {

    @GetMapping("/users/{user}")
    fun getUser(@PathVariable("user") user: String): User {
        return User(firstName = "Tommy", lastName = "Cat", username = "tommythecat")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(SandboxApi::class.java, *args)
        }
    }
}

