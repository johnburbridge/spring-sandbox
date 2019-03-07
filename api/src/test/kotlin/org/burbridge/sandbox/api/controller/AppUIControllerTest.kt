package org.burbridge.sandbox.api.controller

import org.burbridge.sandbox.api.SandboxApi
import org.burbridge.sandbox.api.config.WebSecurityConfig
import org.burbridge.sandbox.api.security.AppUserDetailsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
@WebMvcTest(AppUIController::class)
@ContextConfiguration(classes = [SandboxApi::class, WebSecurityConfig::class])
class AppUIControllerTest {

    @MockBean
    lateinit var appUserDetailsService: AppUserDetailsService

    @Autowired
    lateinit var mockMvc: MockMvc

    @WithMockUser("test@metabuild.org", roles = ["USER"])
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