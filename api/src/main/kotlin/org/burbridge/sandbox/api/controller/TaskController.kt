package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.domain.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/tasks"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TaskController {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @GetMapping("/")
    fun getAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    @GetMapping("/{username}")
    fun getTasksByAuthor(@PathVariable username: String): List<Task> {
        return taskRepository.findByAuthor_Username(username)
    }
}
