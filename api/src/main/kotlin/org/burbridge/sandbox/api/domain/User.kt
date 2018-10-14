package org.burbridge.sandbox.api.domain

import org.burbridge.spring.common.dto.UserDto
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
) : AuditableEntity() {
        fun toDto(): UserDto {
                return UserDto(
                        id = this.id.toInt(),
                        firstName = this.firstName,
                        lastName = this.lastName,
                        username = this.username
                )
        }
}
