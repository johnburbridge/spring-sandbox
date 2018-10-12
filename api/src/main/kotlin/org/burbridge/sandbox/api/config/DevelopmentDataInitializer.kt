package org.burbridge.sandbox.api.config

import org.burbridge.sandbox.api.repository.ArticleRepository
import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.sandbox.api.domain.Article
import org.burbridge.sandbox.api.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DevelopmentDataInitializer {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var articleRepository: ArticleRepository

    fun initialize() {
        userRepository.save(
                User(1L, "jburbridge", "John", "Burbridge")
        )
        val u1 = checkNotNull(userRepository.findByUsername("jburbridge"))
        articleRepository.saveAll(setOf(
                Article(id = 2L, author = u1, title = "The first post", content = "This is a rather boring first post"),
                Article(id = 3L, author = u1, title = "The second post", content = "This is another rather boring first post"),
                Article(id = 4L, author = u1, title = "The third post", content = "This is yet another rather boring first post!")
        ))
    }

    fun clean() {
        articleRepository.deleteAll()
        articleRepository.flush()
        userRepository.deleteAll()
        userRepository.flush()
    }
}