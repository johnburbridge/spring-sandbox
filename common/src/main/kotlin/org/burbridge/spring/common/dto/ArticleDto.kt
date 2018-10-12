package org.burbridge.spring.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class ArticleDto(

        @field:JsonProperty("author")
        val author: AuthorDto? = null,

        @field:JsonProperty("id")
        val id: Int? = null,

        @field:JsonProperty("title")
        val title: String? = null,

        @field:JsonProperty("content")
        val content: String? = null
)