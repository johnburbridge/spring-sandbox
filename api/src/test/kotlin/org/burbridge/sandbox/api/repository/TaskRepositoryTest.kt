package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.AbstractSeededIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class TaskRepositoryTest : AbstractSeededIntegrationTest() {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Test
    fun `Can query a list of all tasks`() {
        val tasks = taskRepository.findAll()
        assertTrue(tasks.isNotEmpty())
        assertEquals(3, tasks.count())
    }

    @Test
    fun `Can query tasks by author`() {
        val tasks = taskRepository.findByAuthor_Username("jburbridge")
        assertTrue(tasks.isNotEmpty())
        assertEquals(3, tasks.count())
        assertEquals("Passw0rd", tasks.first().author.password)
    }
}