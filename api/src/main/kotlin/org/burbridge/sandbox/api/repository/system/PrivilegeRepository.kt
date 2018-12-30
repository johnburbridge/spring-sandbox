package org.burbridge.sandbox.api.repository.system

import org.burbridge.sandbox.api.domain.system.Privilege
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long> {
    fun findByName(name: String): Privilege?
}
