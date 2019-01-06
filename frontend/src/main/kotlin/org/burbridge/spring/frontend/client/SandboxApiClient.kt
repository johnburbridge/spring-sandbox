package org.burbridge.spring.frontend.client

import org.burbridge.spring.common.dto.UsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class SandboxApiClient(
        @Autowired val restTemplate: RestTemplate,
        @Autowired val sandboxApiUriBuilder: SandboxApiUriBuilder) {

    fun getAllUsers(): UsersResponse? {
        val usersUri = sandboxApiUriBuilder.getUsersURI()
        return restTemplate.getForObject(usersUri, UsersResponse::class.java)
    }
}
