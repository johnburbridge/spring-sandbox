package org.burbridge.sandbox.api.domain.system

import javax.persistence.*

@Entity
@Table(name = "privileges")
data class Privilege(
        @Id @GeneratedValue
        val id: Long = 0L,
        val name: String,
        @ManyToMany(mappedBy = "privileges")
        val roles: Collection<Role> = emptyList()
)
