package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.repository.ArticleRepository
import org.burbridge.sandbox.api.domain.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/articles"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ArticleController {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @GetMapping("/")
    fun getAllArticles(): List<Article> {
        return articleRepository.findAll()
    }

    @GetMapping("/{username}")
    fun getArticlesByAuthor(@PathVariable username: String): List<Article> {
        return articleRepository.findByAuthor_Username(username)
    }
}
