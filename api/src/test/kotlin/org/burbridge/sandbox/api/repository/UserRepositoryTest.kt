package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.AbstractSeededIntegrationTest
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class UserRepositoryTest : AbstractSeededIntegrationTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Test
    fun `Can query a list of all users`() {
        val users = userRepository.findAll()
        assertTrue(users.isNotEmpty())
        assertEquals(2, users.count())
    }

    @Test
    fun `Can query a user by username`() {
        val user = userRepository.findByEmail("test@metabuild.org")
        assertNotNull(user)
        assertTrue(encoder.matches("test", user!!.password))
    }
}