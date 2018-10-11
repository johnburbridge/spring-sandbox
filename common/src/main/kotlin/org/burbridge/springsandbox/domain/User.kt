package org.burbridge.springsandbox.domain

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue
        val id: Long,
        @Column(unique = true)
        val username: String,
        val firstName: String,
        val lastName: String
) : AuditableEntity()
