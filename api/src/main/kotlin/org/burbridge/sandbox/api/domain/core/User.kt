package org.burbridge.sandbox.api.domain.core

import org.burbridge.spring.common.dto.UserDto
import java.io.Serializable
import java.util.*
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
        val lastLogin: Date? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        val roles: MutableCollection<Role> = arrayListOf()
) : Serializable {
        fun toDto(): UserDto {
                return toDto(this)
        }

        private fun toDto(user: User): UserDto {
                return UserDto(id = user.id.toInt(),
                        email = user.email,
                        password = user.password,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        enabled = user.enabled,
                        lastLogin = user.lastLogin
                )
        }
}