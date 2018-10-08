package org.burbridge.springsandbox.domain

data class Article(
        val title: String,
        val content: String,
        val author: User
)