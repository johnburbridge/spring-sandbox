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

    @Test
    fun `Can get a DTO from an user entity`() {
        val user = userRepository.findByUsername("jburbridge")
        val userDto = user.toDto()
        assertNotNull(userDto)
        assertEquals(user.firstName, userDto.firstName)
        assertEquals(user.lastName, userDto.lastName)
        assertEquals(user.username, userDto.username)
    }
}