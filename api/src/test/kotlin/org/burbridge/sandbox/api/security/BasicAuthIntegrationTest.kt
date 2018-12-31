package org.burbridge.sandbox.api.security

import org.burbridge.sandbox.api.AbstractSeededIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class BasicAuthIntegrationTest : AbstractSeededIntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithUserDetails(value = "test@metabuild.org", userDetailsServiceBeanName = "userDetailsService")
    fun `Can authenticate with a valid user`() {
        mockMvc.perform(get("/"))
                .andExpect(status().`is`(301))
    }

    @Test
    fun `Cannot authenticate with an invalid user`() {
        mockMvc.perform(get("/").with(httpBasic("foo","bar")))
                .andExpect(status().`is`(401))
    }
    @Test
    @WithAnonymousUser
    fun `Access forbidden with no user`() {
        mockMvc.perform(get("/"))
                .andExpect(status().`is`(401))
    }
}
