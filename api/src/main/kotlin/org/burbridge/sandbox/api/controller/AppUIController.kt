package org.burbridge.sandbox.api.controller

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@Controller
class AppUIController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    fun homeRedirectToSwagger(): ModelAndView {
        return ModelAndView("redirect:/swagger-ui.html")
    }
}
