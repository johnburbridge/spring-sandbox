package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {

    fun findByTitle(title: String): List<Article>
    fun findByAuthor_Username(username: String): List<Article>
}