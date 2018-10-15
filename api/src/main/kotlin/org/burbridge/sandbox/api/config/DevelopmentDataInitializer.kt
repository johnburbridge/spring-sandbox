package org.burbridge.sandbox.api.config

import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.repository.UserRepository
import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DevelopmentDataInitializer {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    fun initialize() {
        userRepository.save(
                User(1L, "jburbridge", "Passw0rd")
        )
        val u1 = checkNotNull(userRepository.findByUsername("jburbridge"))
        taskRepository.saveAll(setOf(
                Task(id = 2L, author = u1, name = "task1", description = "The first task", content = "This is a rather boring first post"),
                Task(id = 3L, author = u1, name = "task2", description = "The second task", content = "This is another rather boring first post"),
                Task(id = 4L, author = u1, name = "task3", description = "The 3rd task", content = "This is yet another rather boring first post!")
        ))
    }

    fun clean() {
        taskRepository.deleteAll()
        taskRepository.flush()
        userRepository.deleteAll()
        userRepository.flush()
    }
}