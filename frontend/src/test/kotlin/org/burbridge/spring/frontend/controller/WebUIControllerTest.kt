package org.burbridge.spring.frontend.controller

import org.burbridge.spring.frontend.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@AutoConfigureMockMvc
class WebUIControllerTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `Can access home`() {
        mvc = MockMvcBuilders.standaloneSetup(WebUIController()).build()
        val result = mvc.perform(get("/"))
        result.andDo(print())
                .andExpect(status().isOk)
                .andExpect(view().name("home"))
    }
}