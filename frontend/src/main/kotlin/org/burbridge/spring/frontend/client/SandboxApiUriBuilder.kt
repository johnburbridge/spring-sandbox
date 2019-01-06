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

    fun getUsersURI(offset: Int = 0): URI {
        return UriComponentsBuilder.newInstance()
                .scheme(sandboxApiScheme)
                .host(sandboxApiHost)
                .port(sandboxApiPort)
                .path("/users/")
                .queryParam("offset", offset)
                .build()
                .toUri()
    }
}
