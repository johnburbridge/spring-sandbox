package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.burbridge.spring.common.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.util.*
import javax.validation.Valid

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class PrincipalController(@Autowired val userRepository: UserRepository) {

    @GetMapping("/auth")
    fun getAuthorities(webRequest: WebRequest): List<String>? {
        val principal = webRequest.userPrincipal as UsernamePasswordAuthenticationToken
        logger.info { "Authorization request for ${principal.name} granted ${principal.authorities}" }
        userRepository.updateLastLogin(Date())
        return principal.authorities.map { it.authority }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid userDto: UserDto): UserDto {
        val user = toEntity(userDto)
        val userCreated = userRepository.saveAndFlush(user)
        logger.info("Created new user: ${userCreated.email}")
        return user.toDto()
    }

    // TODO refactor this (duplicate code)
    private fun toEntity(userDto: UserDto): User {
        return User(id = userDto.id!!.toLong(),
                email = userDto.email!!,
                password = userDto.password!!,
                firstName = userDto.firstName!!,
                lastName = userDto.lastName!!,
                enabled = userDto.enabled
        )
    }
}
