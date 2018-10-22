package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.User
import org.burbridge.sandbox.api.error.RecordNotFoundException
import org.burbridge.sandbox.api.repository.UserRepository
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
    fun getAllUsers(): List<UserDto> {
        val users = userRepository.findAll()
        val usersDto = users.map { user -> toDto(user) }
        return usersDto
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): UserDto {
        try {
            return toDto(userRepository.findByUsername(username))
        } catch (exception: EmptyResultDataAccessException) {
            logger.error("Could not find user: $username", exception)
            throw RecordNotFoundException(username)
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto): UserDto {
        val user = toEntity(userDto)
        val userCreated = userRepository.saveAndFlush(user)
        return toDto(userCreated)
    }

    private fun toEntity(userDto: UserDto): User {
        return User(id = userDto.id!!.toLong(),
                username = userDto.username!!,
                password = userDto.password!!)
    }

    private fun toDto(user: User): UserDto {
        return UserDto(id = user.id.toInt(),
                username = user.username,
                password = user.password)
    }
}
