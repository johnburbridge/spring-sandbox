package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class UserDto(

        @field:JsonProperty("id")
        val id: Int? = null,

        @field:JsonProperty("password", access = JsonProperty.Access.WRITE_ONLY)
        val password: String? = null,

        @field:JsonProperty("matchingPassword", access = JsonProperty.Access.WRITE_ONLY)
        val matchingPassword: String? = null,

        @field:JsonProperty("email")
        val email: String? = null,

        @field:JsonProperty("first_name")
        val firstName: String? = null,

        @field:JsonProperty("last_name")
        val lastName: String? = null,

        @field:JsonProperty("enabled")
        val enabled: Boolean = true
)