package org.burbridge.sandbox.api.domain

import javax.persistence.*

@Entity
@Table(name = "articles")
class Article(
        @Id
        @GeneratedValue
        val id: Long,
        val title: String,
        @Column(columnDefinition = "TEXT")
        val content: String,
        @ManyToOne
        val author: User
) : AuditableEntity()