package org.burbridge.spring.frontend.controller

import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.burbridge.spring.frontend.AbstractIntegrationTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.WebApplicationContext


class WebUIControllerIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var context: WebApplicationContext
    lateinit var mvc: MockMvc

    @BeforeAll
    fun configureContext() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
    }

    @Test
    fun `Can access home when authenticated`() {

        val postResult = mvc.perform(post("/perform_login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(UrlEncodedFormEntity(listOf(
                        BasicNameValuePair("username", "test@metabuild.org"),
                        BasicNameValuePair("password", "test")
                )))))
        postResult.andDo(print())
                .andExpect(status().isFound)
                .andExpect(redirectedUrl("/home"))
    }

    @Test
    @WithAnonymousUser
    fun `Is redirected to login when not authenticated`() {

        val result = mvc.perform(get("/"))
        result.andDo(print())
                .andExpect(status().isFound)
                .andExpect(redirectedUrl("http://localhost/login"))
    }

    @Test
    @WithMockUser("test@metabuild.org", authorities = ["ROLE_USER"])
    fun `Cannot access admin with test account`() {

        val result = mvc.perform(get("/admin"))
        result.andDo(print())
                .andExpect(status().isForbidden)
                .andExpect(forwardedUrl("/noAccess"))
    }

// TODO: figure this out - using WithMockUser won't work. Has to be a real user and needs a valid backend session
//    @Test
//    @WithMockUser("admin@metabuild.org", authorities = ["ROLE_ADMIN"])
//    fun `Can access admin with admin account`() {
//
//        val result = mvc.perform(get("/admin"))
//        result.andDo(print())
//                .andExpect(status().isOk)
//                .andExpect(view().name("admin"))
//                .andExpect(model().attributeExists("users"))
//    }

    @Test
    @WithAnonymousUser
    fun `Can register a valid user`() {

        val postResult = mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(UrlEncodedFormEntity(listOf(
                        BasicNameValuePair("firstName", "Tyler"),
                        BasicNameValuePair("lastName", "Durden"),
                        BasicNameValuePair("email", "tyler@projectmayhem.org"),
                        BasicNameValuePair("password", "Sp4c3M0nk3yz"),
                        BasicNameValuePair("confirmPassword", "Sp4c3M0nk3yz")
                )))))
        postResult.andDo(print())
                .andExpect(status().isOk)
    }

    @Test
    @WithAnonymousUser
    fun `Cannot register with un-matching passwords`() {

        val postResult = mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(UrlEncodedFormEntity(listOf(
                        BasicNameValuePair("firstName", "Marla"),
                        BasicNameValuePair("lastName", "Singler"),
                        BasicNameValuePair("email", "msinger@projectmayhem.org"),
                        BasicNameValuePair("password", "Sp4c3M0nk3yz"),
                        BasicNameValuePair("confirmPassword", "")
                )))))
        // response page is 200 OK but contains validation errors
        postResult.andDo(print())
                .andExpect(status().isOk)
                .andExpect(model().hasErrors<MethodArgumentNotValidException>())
                .andExpect(model().attributeExists("user"))
    }
}