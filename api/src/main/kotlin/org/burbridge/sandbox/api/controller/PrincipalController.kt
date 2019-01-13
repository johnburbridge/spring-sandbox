package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class PrincipalController {

    @GetMapping("/auth")
    fun getAuthorities(webRequest: WebRequest): List<String>? {
        val principal = webRequest.userPrincipal as UsernamePasswordAuthenticationToken
        logger.info { "Authorization request for ${principal.name} granted ${principal.authorities}" }
        return principal.authorities.map { it.authority }
    }
}
