package org.burbridge.sandbox.api.repository.system

import org.burbridge.sandbox.api.domain.system.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
}