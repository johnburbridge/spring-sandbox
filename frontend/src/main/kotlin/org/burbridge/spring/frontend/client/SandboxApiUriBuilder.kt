package org.burbridge.spring.frontend.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class SandboxApiUriBuilder(
    @Value("\${sandbox.api.scheme}")
    val sandboxApiScheme: String,
    @Value("\${sandbox.api.host}")
    val sandboxApiHost: String,
    @Value("\${sandbox.api.port}")
    val sandboxApiPort: String
) {

    @Value("\${sandbox.api.endpoints.authorization}")
    lateinit var authorizationEndpoint: String

    @Value("\${sandbox.api.endpoints.users}")
    lateinit var usersEndpoint: String


    fun getUsersURI(offset: Int = 0): URI {
        return UriComponentsBuilder.newInstance()
                .scheme(sandboxApiScheme)
                .host(sandboxApiHost)
                .port(sandboxApiPort)
                .path("$usersEndpoint/")
                .queryParam("offset", offset)
                .build()
                .toUri()
    }

    fun getSingleUserURI(username: String): URI {
        return UriComponentsBuilder.newInstance()
                .scheme(sandboxApiScheme)
                .host(sandboxApiHost)
                .port(sandboxApiPort)
                .path("$usersEndpoint/$username")
                .build()
                .toUri()
    }

    fun getAuthenticationURI(): URI {
        return UriComponentsBuilder.newInstance()
                .scheme(sandboxApiScheme)
                .host(sandboxApiHost)
                .port(sandboxApiPort)
                .path(authorizationEndpoint)
                .build()
                .toUri()
    }
}
