package org.burbridge.springsandbox.domain

import javax.persistence.*

@Entity
@Table(name = "articles")
data class Article(
        @Id
        @GeneratedValue
        val id: Long,
        val title: String,
        val content: String,
        @ManyToOne
        val author: User
)