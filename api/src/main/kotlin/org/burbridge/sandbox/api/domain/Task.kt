package org.burbridge.sandbox.api.domain

import javax.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
        @Id
        @GeneratedValue
        val id: Long,
        val name: String,
        val description: String,
        @Column(columnDefinition = "TEXT")
        val content: String
) : AuditableEntity()