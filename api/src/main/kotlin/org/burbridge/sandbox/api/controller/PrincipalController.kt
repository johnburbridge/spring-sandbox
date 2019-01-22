package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.util.*

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
}
