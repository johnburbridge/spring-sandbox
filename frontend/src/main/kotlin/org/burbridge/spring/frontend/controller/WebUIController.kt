package org.burbridge.spring.frontend.controller

import mu.KotlinLogging
import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.frontend.client.SandboxApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.WebRequest
import java.security.Principal

private val logger = KotlinLogging.logger {}

@Controller
class WebUIController {

    @Autowired
    lateinit var sandboxApiClient: SandboxApiClient

    @GetMapping(path =  ["/", "/home"])
    fun home(request: WebRequest, model: Model): String {
        val principal = request.userPrincipal?.name
        model.addAttribute("username", principal)
        logger.info { "Got /home request from $principal" }
        return "home"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/registration")
    fun register(request: WebRequest, model: Model): String {
        model.addAttribute("user", UserDto())
        return "register"
    }

    @GetMapping("/admin")
    fun admin(request: WebRequest, model: Model): String {
        val principal = request.userPrincipal?.name
        val users = sandboxApiClient.getAllUsers()
        model.addAttribute("users", users)
        logger.info { "Got /admin request from $principal" }
        return "admin"
    }

    @GetMapping("/noAccess")
    fun denied(request: WebRequest, model: Model): String {
        val principal = request.userPrincipal?.name
        model.addAttribute("username", principal)
        logger.info { "Denied request to $principal" }
        return "denied"
    }
}
