package org.burbridge.sandbox.api.repository

import org.burbridge.sandbox.api.domain.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {

    fun findByName(name: String): List<Task>
    fun findByAuthor_Username(username: String): List<Task>
}