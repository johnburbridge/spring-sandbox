package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class UserDto(

        @field:JsonProperty("firstName")
        val firstName: String? = null,

        @field:JsonProperty("lastName")
        val lastName: String? = null,

        @field:JsonProperty("id")
        val id: Int? = null,

        @field:JsonProperty("username")
        val username: String? = null
)