package org.burbridge.spring.frontend.controller

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.WebRequest

private val logger = KotlinLogging.logger {}

@Controller
class WebUIController {

    @GetMapping(path =  ["/", "/home"])
    fun home(request: WebRequest, model: Model): String {
        val principal = request.userPrincipal?.name
        logger.info { "Got / request from $principal" }
        return "home"
    }

    @GetMapping("/login")
    fun login(request: WebRequest, model: Model): String {
        return "login"
    }

    @GetMapping("/admin")
    fun admin(request: WebRequest, model: Model): String {
        val principal = request.userPrincipal?.name
        logger.info { "Got /admin request from $principal" }
        return "admin"
    }
}
