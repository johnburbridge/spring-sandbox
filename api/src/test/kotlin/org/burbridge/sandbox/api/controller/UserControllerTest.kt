package org.burbridge.sandbox.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.service.core.UserService
import org.burbridge.spring.common.dto.UserDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.net.URI

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(secure = false)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    // TODO figure out why this doesn't work
    // https://discuss.kotlinlang.org/t/kotlin-junit-mockito/278
    //
//    @Test
    fun `Verify controller can create new user`() {
        // arrange
        val userStub = User(id = 0L,
                email = "unit@metabuild.org",
                password = "test",
                firstName = "Unit",
                lastName = "Test",
                enabled = true,
                tokenExpired = false,
                roles = emptyList(),
                lastLogin = null)
        `when`(userService.save(any(User::class.java))).thenReturn(userStub)

        // act
        val userDto = UserDto(
                id = userStub.id.toInt(),
                email = userStub.email,
                password = userStub.password,
                firstName = userStub.firstName,
                lastName = userStub.lastName,
                enabled = true)
        val mapper = jacksonObjectMapper()
        val userJson = mapper.writeValueAsString(userDto)
        val mvcResult = mockMvc.perform((MockMvcRequestBuilders.post(URI("http://localhost:8080/users/")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andReturn()

        // assert
        val status = mvcResult.response.status
        val userResult = mapper.readValue<UserDto>(mvcResult.response.contentAsString, UserDto::class.java)
        Assertions.assertEquals(HttpStatus.CREATED.value(), status)
        Assertions.assertEquals("Unit", userResult.firstName)
        Assertions.assertEquals("Test", userResult.lastName)
    }
}