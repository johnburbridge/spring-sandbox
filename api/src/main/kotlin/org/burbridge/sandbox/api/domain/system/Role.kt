package org.burbridge.sandbox.api.domain.system

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        @Id @GeneratedValue
        val id: Long = 0L,
        val name: String,
        @ManyToMany(mappedBy = "roles")
        var users: Collection<User> = emptyList(),
        @ManyToMany
        @JoinTable(name = "roles_privileges",
                joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
        var privileges: Collection<Privilege> = emptyList()
)