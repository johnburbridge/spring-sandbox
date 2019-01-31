package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.error.RecordNotFoundException
import org.burbridge.sandbox.api.service.core.UserService
import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.common.dto.UsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(path = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/")
    fun getAllUsers(): UsersResponse {
        val users = userService.findAll()
        val usersDto = users.map { user -> user.toDto() }
        return UsersResponse(total = usersDto.count(), offset = 0, users = usersDto)
    }

    @GetMapping("/{email}")
    fun getUserByEmail(@PathVariable email: String): UserDto {
        try {
            val user = userService.findByEmail(email)
            return user!!.toDto()
        } catch (exception: EmptyResultDataAccessException) {
            logger.error("Could not find user: $email", exception)
            throw RecordNotFoundException(email)
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto): UserDto {
        val user = toEntity(userDto)
        val userCreated = userService.save(user)
        logger.info("Created new user: ${userCreated.email}")
        return userCreated.toDto()
    }

    private fun toEntity(userDto: UserDto): User {
        return User(id = userDto.id.toLong(),
                email = userDto.email!!,
                password = userDto.password!!,
                firstName = userDto.firstName!!,
                lastName = userDto.lastName!!,
                enabled = userDto.enabled
        )
    }
}
