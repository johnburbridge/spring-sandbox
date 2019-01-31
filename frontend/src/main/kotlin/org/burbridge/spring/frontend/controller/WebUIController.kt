package org.burbridge.spring.frontend.controller

import mu.KotlinLogging
import org.burbridge.spring.common.dto.UserDto
import org.burbridge.spring.frontend.client.SandboxApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import javax.validation.Valid

private val logger = KotlinLogging.logger {}

@Controller
class WebUIController(@Autowired
                      val sandboxApiClient: SandboxApiClient) {

    @GetMapping(path =  ["/", "/home"])
    fun home(model: Model, @AuthenticationPrincipal principal: User): String {
        logger.info { "Got /home request from ${principal.username}" }
        val user = sandboxApiClient.getUser(principal.username)
        model.addAttribute("user", user)
        return "home"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/registration")
    fun registrationForm(request: WebRequest, model: Model): String {
        model.addAttribute("user", UserDto())
        return "register"
    }

    @PostMapping("/registration")
    fun registrationPost(@ModelAttribute("user")
                         @Valid userDto: UserDto,
                         result: BindingResult,
                         request: WebRequest,
                         errors: Errors,
                         model: Model): String {

        val registeredUserDto: UserDto?
        if (!result.hasErrors()) {
            registeredUserDto = sandboxApiClient.register(userDto)
            if (registeredUserDto == null) {
                result.rejectValue("email","Unable to register this account")
                model.addAttribute("user", userDto)
            } else {
                model.addAttribute("user", registeredUserDto)
                return "home"
            }
        } else {
            model.addAttribute("user", userDto)
        }
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
