package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TasksResponse(
        @field:JsonProperty("total")
        val total: Int = 0,

        @field:JsonProperty("offset")
        val offset: Int = 0,

        @field:JsonProperty("tasks")
        val tasks: List<TaskDto> = emptyList()
)