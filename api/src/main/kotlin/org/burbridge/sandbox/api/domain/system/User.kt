package org.burbridge.sandbox.api.domain.system

import org.burbridge.spring.common.dto.UserDto
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id @GeneratedValue
        val id: Long,
        @Column(unique = true, nullable = false)
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val enabled: Boolean = false,
        var tokenExpired: Boolean = false,
        @ManyToMany
        @JoinTable(name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        val roles: Collection<Role> = emptyList()
) {
        fun toDto(): UserDto {
                return toDto(this)
        }

        fun toDto(user: User): UserDto {
                return UserDto(id = user.id.toInt(),
                        email = user.email,
                        password = user.password,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        enabled = user.enabled
                )
        }
}