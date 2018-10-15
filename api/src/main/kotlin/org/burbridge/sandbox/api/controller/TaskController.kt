package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.domain.User
import org.burbridge.spring.common.dto.TaskDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody taskDto: TaskDto): Task {
        return taskRepository.saveAndFlush(
                Task(id = 0L,
                    name = taskDto.name!!,
                    description = taskDto.description!!,
                    content = taskDto.content!!,
                    author = User(id = 0L,
                            username = taskDto.author?.username!!,
                            password = taskDto.author?.password!!
                    )
                )
        )
    }
}
