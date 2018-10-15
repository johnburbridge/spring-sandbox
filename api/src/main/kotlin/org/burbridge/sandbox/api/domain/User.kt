package org.burbridge.sandbox.api.domain

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue
        val id: Long,
        @Column(unique = true, nullable = false)
        val username: String,
        val password: String
) : AuditableEntity()