package org.burbridge.sandbox.api.repository.system

import org.burbridge.sandbox.api.domain.system.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository  : JpaRepository<Role, Long> {
    fun findByName(name: String): Role?
}