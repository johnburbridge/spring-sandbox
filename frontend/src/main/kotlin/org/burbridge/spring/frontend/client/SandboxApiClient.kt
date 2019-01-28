package org.burbridge.spring.frontend.client

import org.apache.tomcat.util.codec.binary.Base64
import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.common.dto.UsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.security.Principal

@Component
class SandboxApiClient(
        @Autowired val restTemplate: RestTemplate,
        @Autowired val sandboxApiUriBuilder: SandboxApiUriBuilder) {

    fun getAllUsers(): UsersResponse? {
        val usersUri = sandboxApiUriBuilder.getUsersURI()
        return restTemplate.getForObject(usersUri, UsersResponse::class.java)
    }

    fun getUser(username: String): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(username)
        return restTemplate.getForObject(singleUserUri, UserDto::class.java)
    }

    fun createUser(userDto: UserDto): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(checkNotNull(userDto.email))
        return restTemplate.postForObject(singleUserUri, userDto, UserDto::class.java)
    }

    fun updateUser(userDto: UserDto): UserDto? {
        val singleUserUri: URI = sandboxApiUriBuilder.getSingleUserURI(checkNotNull(userDto.email))
        return restTemplate.patchForObject(singleUserUri, userDto, UserDto::class.java)
    }

    fun authenticate(username: String, pass: String): ResponseEntity<MutableList<String>> {
        val entity = HttpEntity<Principal>(createHeaders(username, pass))
        val authenticationUri = sandboxApiUriBuilder.getAuthenticationURI()
        return restTemplate.exchange(authenticationUri, HttpMethod.GET, entity, mutableListOf<String>().javaClass)
    }

    private fun createHeaders(username: String, password: String): HttpHeaders {
        val headers = object : HttpHeaders() {
            init {
                set(ACCEPT, MediaType.APPLICATION_JSON.toString())
            }
        }
        val basic = String(Base64.encodeBase64("$username:$password".toByteArray()))
        headers.set("Authorization", "Basic $basic")

        return headers
    }
}
