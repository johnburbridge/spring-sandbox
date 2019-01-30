package org.burbridge.sandbox.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.tomcat.util.codec.binary.Base64
import org.burbridge.spring.common.dto.UserDto
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.net.URI

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    @Autowired
    private lateinit var context: WebApplicationContext
    lateinit var mvc: MockMvc

    @BeforeAll
    fun configureContext() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `Valid user can authenticate`() {

        val result = mvc.perform(get(URI("http://localhost:8080/auth"))
                        .headers(createHeaders("test@metabuild.org","test")))
        result.andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("[\"ROLE_USER\"]"))
    }

    @Test
    @WithAnonymousUser
    fun `Invalid user gets error`() {

        val result = mvc.perform(get(URI("http://localhost:8080/auth"))
                        .headers(createHeaders("invalid@metabuild.org","user")))

        result.andDo(print())
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `Valid user can register`() {

        val userDto = UserDto(
                id = 100,
                email = "tyler@projectmayhem.org",
                password = "Sp4c3M0nk3yz",
                matchingPassword = "Sp4c3M0nk3yz",
                firstName = "Tyler",
                lastName = "Durden",
                enabled = true)
        val mapper = jacksonObjectMapper()
        val userJson = mapper.writeValueAsString(userDto)
        val result = mvc.perform((post(URI("http://localhost:8080/register")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson))

        result.andDo(print())
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email").value("tyler@projectmayhem.org"))
    }

    @Test
    fun `Invalid registration - not matching passwords`() {

        val userDto = UserDto(
                id = 100,
                email = "msinger@projectmayhem.org",
                password = "Sp4c3M0nk3yz",
                matchingPassword = "",
                firstName = "Marla",
                lastName = "Singer",
                enabled = true)
        val mapper = jacksonObjectMapper()
        val userJson = mapper.writeValueAsString(userDto)
        val result = mvc.perform((post(URI("http://localhost:8080/register")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson))

        result.andDo(print())
                .andExpect(status().isBadRequest)
    }


    private fun createHeaders(username: String, password: String): HttpHeaders {
        val headers = object : HttpHeaders() {
            init {
                set(ACCEPT, MediaType.APPLICATION_JSON.toString())
            }
        }
        val authorization = "$username:$password"
        val basic = String(Base64.encodeBase64(authorization.toByteArray()))
        headers.set("Authorization", "Basic $basic")

        return headers
    }
}