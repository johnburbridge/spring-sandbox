package org.burbridge.sandbox.api.repository

import org.burbridge.springsandbox.domain.Article
import org.burbridge.springsandbox.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {

    fun findByTitle(title: String): List<Article>
    fun findByAuthor(author: User): List<Article>
}