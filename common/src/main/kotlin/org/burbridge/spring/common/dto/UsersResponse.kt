package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UsersResponse(
        @field:JsonProperty("total")
        val total: Int = 0,

        @field:JsonProperty("offset")
        val offset: Int = 0,

        @field:JsonProperty("users")
        val users: List<UserDto> = emptyList()
)