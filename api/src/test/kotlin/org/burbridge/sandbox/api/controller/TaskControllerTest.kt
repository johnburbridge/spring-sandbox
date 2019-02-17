package org.burbridge.sandbox.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.burbridge.sandbox.api.SandboxApi
import org.burbridge.sandbox.api.config.WebSecurityConfig
import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.security.AppUserDetailsService
import org.burbridge.spring.common.dto.TaskDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.net.URI

@ExtendWith(SpringExtension::class)
@WebMvcTest(TaskController::class)
@ContextConfiguration(classes = [SandboxApi::class, WebSecurityConfig::class])
class TaskControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var appUserDetailsService: AppUserDetailsService

    @MockBean
    lateinit var taskRepository: TaskRepository

    @WithMockUser("user@metabuild.org", roles = ["USER"])
    @Test
    fun `Verify controller can add a new task`() {
        // arrange
        val taskStub = Task(id = 0L,
                name = "Test1",
                description = "A test task",
                content = """
                    Multi-line content goes
                    here.
                """.trimIndent()
        )
        `when`(taskRepository.saveAndFlush(any(Task::class.java))).thenReturn(taskStub)

        //act
        val taskDto = TaskDto(
                id = taskStub.id.toInt(),
                name = taskStub.name,
                description = taskStub.description,
                content = taskStub.content
        )
        val mapper = jacksonObjectMapper()
        val taskJson = mapper.writeValueAsString(taskDto)
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI("http://localhost:8080/tasks/"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andReturn()

        // assert
        val status = mvcResult.response.status
        val taskResult = mapper.readValue<TaskDto>(mvcResult.response.contentAsString, TaskDto::class.java)
        assertEquals(HttpStatus.CREATED.value(), status)
        assertEquals("Test1", taskResult.name)
    }
}