package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.AbstractSeededIntegrationTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserRepositoryTest : AbstractSeededIntegrationTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Can query a list of all users`() {
        val users = userRepository.findAll()
        assertTrue(users.isNotEmpty())
        assertEquals(1, users.count())
    }

    @Test
    fun `Can query a user by username`() {
        val user = userRepository.findByUsername("jburbridge")
        assertNotNull(user)
        assertEquals("Burbridge", user.lastName)
    }
}