package org.burbridge.sandbox.api.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.net.URI

@ExtendWith(SpringExtension::class)
@WebMvcTest(AppUIController::class)
@AutoConfigureMockMvc(secure = false)
class AppUIControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Home redirects to swagger`() {

        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI("http://localhost:8080/"))
                .contentType(MediaType.TEXT_HTML))
                .andReturn()

        // assert
        val status = mvcResult.response.status
        val result = mvcResult.response.redirectedUrl
        assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), status)
        assertEquals("/swagger-ui.html", result)
    }
}