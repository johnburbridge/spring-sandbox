package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.burbridge.spring.common.annotations.PasswordMatches
import javax.annotation.Generated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@PasswordMatches
@Generated("com.robohorse.robopojogenerator")
data class UserDto(

        @field:JsonProperty("id")
        val id: Int? = null,

        @NotNull
        @NotBlank
        @field:JsonProperty("password")
        val password: String? = null,

        @field:JsonProperty("matchingPassword")
        val matchingPassword: String? = null,

        @NotNull
        @NotBlank
        @field:JsonProperty("email")
        val email: String? = null,

        @NotNull
        @NotBlank
        @field:JsonProperty("first_name")
        val firstName: String? = null,

        @NotNull
        @NotBlank
        @field:JsonProperty("last_name")
        val lastName: String? = null,

        @field:JsonProperty("enabled")
        val enabled: Boolean = true
)