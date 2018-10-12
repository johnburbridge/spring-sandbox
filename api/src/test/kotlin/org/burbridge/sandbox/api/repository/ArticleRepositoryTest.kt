package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.AbstractSeededIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ArticleRepositoryTest : AbstractSeededIntegrationTest() {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Test
    fun `Can query a list of all articles`() {
        val articles = articleRepository.findAll()
        assertTrue(articles.isNotEmpty())
        assertEquals(3, articles.count())
    }

    @Test
    fun `Can query articles by author`() {
        val articles = articleRepository.findByAuthor_Username("jburbridge")
        assertTrue(articles.isNotEmpty())
        assertEquals(3, articles.count())
        assertEquals("Burbridge", articles.first().author.lastName)
    }
}