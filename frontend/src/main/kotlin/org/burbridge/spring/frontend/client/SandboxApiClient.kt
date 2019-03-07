package org.burbridge.spring.frontend.client

import mu.KotlinLogging
import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.common.dto.UsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

private val logger = KotlinLogging.logger {}

@Component
class SandboxApiClient(
        @Autowired val restTemplate: RestTemplate,
        @Autowired val sandboxApiUriBuilder: SandboxApiUriBuilder) {

    private fun authenticatedRestTemplate(): RestTemplate {
        if (restTemplate.interceptors.isEmpty()) {
            val user = SecurityContextHolder.getContext().authentication.principal as User
            logger.info("Adding Basic Authentication interceptor for ${user.password}")
            restTemplate.interceptors.add(BasicAuthenticationInterceptor(user.username, user.password))
        }
        return restTemplate
    }

    fun authenticate(username: String, password: String): ResponseEntity<MutableList<String>> {
        logger.info("Adding Basic Authentication interceptor for $username")
        restTemplate.interceptors.add(BasicAuthenticationInterceptor(username, password))
        val authenticationUri = sandboxApiUriBuilder.getAuthenticationURI()
        return restTemplate.exchange(authenticationUri, HttpMethod.GET, null, mutableListOf<String>().javaClass)
    }

    fun register(userDto: UserDto): UserDto? {
        val registrationUri = sandboxApiUriBuilder.getRegistrationURI()
        return restTemplate.postForObject(registrationUri, userDto, UserDto::class.java)
    }

    fun getAllUsers(): UsersResponse? {
        val usersUri = sandboxApiUriBuilder.getUsersURI()
        return authenticatedRestTemplate().getForObject(usersUri, UsersResponse::class.java)
    }

    fun getUser(username: String): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(username)
        return authenticatedRestTemplate().getForObject(singleUserUri, UserDto::class.java)
    }

    fun createUser(userDto: UserDto): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(checkNotNull(userDto.email))
        return authenticatedRestTemplate().postForObject(singleUserUri, userDto, UserDto::class.java)
    }

    fun updateUser(userDto: UserDto): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(checkNotNull(userDto.email))
        return restTemplate.patchForObject(singleUserUri, userDto, UserDto::class.java)
    }
}
