package org.burbridge.sandbox.api.security

import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationSuccessHandlerImpl(
        @Autowired
        val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(arg0: HttpServletRequest, arg1: HttpServletResponse, arg2: Authentication) {
        userRepository!!.updateLastLogin(Date())
    }

}
