package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.error.RecordNotFoundException
import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.spring.common.dto.TaskDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(path = ["/tasks"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TaskController {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @GetMapping("/")
    fun getAllTasks(): List<TaskDto> {
        return taskRepository.findAll().map { task -> toDto(task) }
    }

    @GetMapping("/{id}")
    fun getTasksByName(@PathVariable id: Long): TaskDto {
        try {
            return toDto(taskRepository.findById(id).get())
        } catch (exception: EmptyResultDataAccessException) {
            logger.error("Could not find task: $id", exception)
            throw RecordNotFoundException(id.toString())
        }
    }

    @GetMapping("/{name}")
    fun getTasksByName(@PathVariable name: String): List<TaskDto> {
        return taskRepository.findByName(name).map { task -> toDto(task) }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody taskDto: TaskDto): TaskDto {
        val task = toEntity(taskDto)
        return toDto(taskRepository.saveAndFlush(task))
    }

    protected fun toEntity(taskDto: TaskDto): Task {
        return Task(id = taskDto.id!!.toLong(),
                name = taskDto.name!!,
                description = taskDto.description!!,
                content = taskDto.content!!
        )
    }

    protected fun toDto(task: Task): TaskDto {
        return TaskDto(id = task.id.toInt(),
                name = task.name,
                description = task.description,
                content = task.content
        )
    }
}
