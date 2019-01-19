package org.burbridge.sandbox.api.controller

import org.apache.tomcat.util.codec.binary.Base64
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.springframework.security.web.SecurityFilterChain
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.WebRequest
import java.net.URI
import java.security.Principal
import javax.servlet.Filter

@ExtendWith(SpringExtension::class)
@WebMvcTest(PrincipalController::class)
@AutoConfigureMockMvc(secure = false)
class PrincipalControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var webRequest: WebRequest

// TODO fix this   @Test
    fun `Valid user can authenticate`() {

        val principalStub = object : Principal {
            override fun getName(): String {
                return "test@metabuild.org"
            }
        }
        `when`(webRequest.userPrincipal).thenReturn(principalStub)
        // need to figure out how to wire this stub into the controller

        val mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URI("http://localhost:8080/auth"))
                        .contentType(MediaType.TEXT_HTML)
                        .headers(createHeaders("test@metabuild.org","test")))
                .andReturn()

        // assert
        val status = mvcResult.response.status
        val result = mvcResult.response.redirectedUrl
        Assertions.assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), status)
        Assertions.assertEquals("/swagger-ui.html", result)
    }

    fun `Invalid user gets error`() {

    }

    fun `Valid user denied access to restricted resource`() {

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